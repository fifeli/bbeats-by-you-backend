package com.sentura.beatsbyyou.service;

import com.sentura.beatsbyyou.constants.EmailTypes;
import com.sentura.beatsbyyou.dto.*;
import com.sentura.beatsbyyou.entity.*;
import com.sentura.beatsbyyou.repository.AuthRepository;
import com.sentura.beatsbyyou.repository.CommentRepository;
import com.sentura.beatsbyyou.repository.OrderRepository;
import com.sentura.beatsbyyou.util.FileUtilizer;
import com.sentura.beatsbyyou.util.ObjectEntityConverter;
import com.sentura.beatsbyyou.util.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${archive.path}")
    private String archivePath;

    @Value("${protocol}")
    private String protocol;

    @Value("${host}")
    private String host;

    @Value("${server.port}")
    private String port;

    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public ResponseDto saveOrder(OrderDto orderDto,MultipartFile file) {

        try{

            Order order = new ObjectEntityConverter().orderDtoToEntity(orderDto);

            if (file != null) {
                String urlPrefix = protocol + "://" + host + "/api/files/";
                String fileName = new FileUtilizer().generateFileName(file.getOriginalFilename());
                String fileLocation = archivePath + "/" + fileName;
                String fileUrl = urlPrefix + fileName;

                if (!new FileUtilizer().writeToDisk(file, Paths.get(archivePath), fileName)) {
                    throw new Exception("File Writing Error Occured!");
                } else {
                    order.setSampleLocalFileLocation(fileLocation);
                    order.setSampleLocalFileUrl(fileUrl);
                }
            }
            order = orderRepository.saveAndFlush(order);

            Map<String,String> map = new HashMap<>();
            map.put("xshortname",order.getFullName().toUpperCase());
            map.put("xname",order.getFullName());
            map.put("xemail",order.getEmail());
            map.put("xmobile",order.getMobile());
            map.put("xpostal",order.getPostalCode());
            map.put("xaddress",order.getAddress());
            map.put("xgenres",order.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")));
            map.put("xmoods",order.getMoods().stream().map(Mood::getName).collect(Collectors.joining(", ")));
            map.put("xtempo",order.getTempoValue().toString());
            map.put("xinstruments",order.getInstruments().stream().map(Instrument::getName).collect(Collectors.joining(", ")));
            map.put("xlength",order.getSongLength());
            map.put("xdescription",order.getDescription());
            map.put("xprice",("$ " + decimalFormat.format(order.getPrice())));

            List<User> users = authRepository.findUserByActiveTrue();

            emailService.sendEmail(map, EmailTypes.INFORM_CUSTOMER,order.getEmail(),null,order);
            if (users != null && !users.isEmpty()) {
                String primaryEmail = users.get(0).getEmail();
                String ccList = users.stream().filter(user->!user.getEmail().equals(primaryEmail)).map(User::getEmail).collect(Collectors.joining(","));
                emailService.sendEmail(map, EmailTypes.INFORM_PRODUCER,primaryEmail,ccList,order);
            }

            return new ResponseDto("success",order.getId());

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto saveComment(CommentDto commentDto) {
        try{
            Order order = orderRepository.findFirstById(commentDto.getId());
            if (order != null) {
                commentDto.setId(0);

                Comment comment = new ObjectEntityConverter().commentDtoToEntity(commentDto);
                comment.setFkOrder(order);
                comment = commentRepository.saveAndFlush(comment);

                commentDto.setId(comment.getId());
            } else {
                throw new Exception("order not found");
            }
            return new ResponseDto("success",commentDto.getId());
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto findOrderById(long id) {
        try{
            Order order = orderRepository.findFirstById(id);
            if (order == null) {
                throw new Exception("order not found");
            }
            return new ResponseDto("success",new ObjectEntityConverter().orderEntityToDto(order));
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto completeOrder(long id) {
        try{
            Order order = orderRepository.findFirstById(id);
            if (order == null) {
                throw new Exception("order not found");
            }
            orderRepository.completeOrder(id);
            return new ResponseDto("success",id);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto findAllCompleteOrders(int pageNumber,int rowsPerPage) {
        try{
            Page<Order> pagedOrders = orderRepository.findOrdersByCompleteTrue(PageRequest.of(pageNumber,rowsPerPage));
            PaginationDto paginationDto = processPaginationPart(pagedOrders,pageNumber,rowsPerPage);

            return new ResponseDto("success",paginationDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto findAllCompleteOrdersByEmail(int pageNumber,int rowsPerPage,String email) {
        try{
            Page<Order> pagedOrders = orderRepository.findOrdersByCompleteTrueAndEmail(email,PageRequest.of(pageNumber,rowsPerPage));
            PaginationDto paginationDto = processPaginationPart(pagedOrders,pageNumber,rowsPerPage);

            return new ResponseDto("success",paginationDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto findOrders(int pageNumber,int rowsPerPage,String type,String keyword) {
        try{
            Page<Order> pagedOrders;
            if (type != null) {
                pagedOrders = orderRepository.findOrdersByType(keyword,type.equals("COMPLETE") ? 1 : 0,PageRequest.of(pageNumber,rowsPerPage));
            } else {
                pagedOrders = orderRepository.findOrdersWithoutType(keyword,PageRequest.of(pageNumber,rowsPerPage));
            }
            PaginationDto paginationDto = processPaginationPart(pagedOrders,pageNumber,rowsPerPage);

            return new ResponseDto("success",paginationDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    private PaginationDto processPaginationPart(Page<Order> pagedOrders,int pageNumber, int rowsPerPage) throws Exception {
        PaginationDto paginationDto;
        List<OrderDto> orderList;
        if (pagedOrders != null) {

            orderList = new ArrayList<>();

            pagedOrders.toList().forEach(order->orderList.add(new ObjectEntityConverter().orderEntityToDto(order)));

            paginationDto = new PaginationDto();
            paginationDto.setPageNumber(pageNumber);
            paginationDto.setRowsPerPage(rowsPerPage);
            paginationDto.setTotalPages(pagedOrders.getTotalPages());
            paginationDto.setPagedData(orderList);

        } else {
            throw new Exception("orders not found");
        }
        return paginationDto;
    }
}
