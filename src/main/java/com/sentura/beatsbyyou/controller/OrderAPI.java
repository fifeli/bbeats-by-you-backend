package com.sentura.beatsbyyou.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sentura.beatsbyyou.dto.CommentDto;
import com.sentura.beatsbyyou.dto.OrderDto;
import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.dto.TypeDto;
import com.sentura.beatsbyyou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StripePaymentAPI stripePaymentAPI;

    @PostMapping("/save")
    public ResponseDto saveOrder(
            @RequestParam("moods")String moods,
            @RequestParam(value = "instruments")String instruments,
            @RequestParam(value = "genres")String genres,
            @RequestParam(value = "fullName")String fullName,
            @RequestParam(value = "email")String email,
            @RequestParam(value = "mobile")String mobile,
            @RequestParam(value = "postalCode")String postalCode,
            @RequestParam(value = "address")String address,
            @RequestParam(value = "songLength")String songLength,
            @RequestParam(value = "description")String description,
            @RequestParam(value = "sampleYoutubeLink")String sampleYoutubeLink,
            @RequestParam(value = "sampleLocalFile")MultipartFile sampleLocalFile,
            @RequestParam(value = "referenceTrack1")String referenceTrack1,
            @RequestParam(value = "referenceTrack2")String referenceTrack2,
            @RequestParam(value = "referenceTrack3")String referenceTrack3,
            @RequestParam(value = "token")String token,
            @RequestParam(value = "tempoValue")Integer tempoValue,
            @RequestParam(value = "price")Double price,
            @RequestParam(value = "charge")Integer charge
    ) {

        if (description.length() > 600000) {
            return new ResponseDto("description should be sort!", "500", null);
        } else {

            ResponseDto responseDto = stripePaymentAPI.createCharge(email,token,charge);

            if (responseDto.getCode().equals("200")) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<TypeDto>>() {}.getType();

                List<TypeDto> moodTypes = gson.fromJson(moods, type);
                List<TypeDto> instrumentTypes = gson.fromJson(instruments, type);
                List<TypeDto> genresTypes = gson.fromJson(genres, type);

                OrderDto orderDto = new OrderDto();
                orderDto.setAddress(address);
                orderDto.setDescription(description);
                orderDto.setEmail(email);
                orderDto.setFullName(fullName);
                orderDto.setGenres(genresTypes);
                orderDto.setInstruments(instrumentTypes);
                orderDto.setMoods(moodTypes);
                orderDto.setMobile(mobile);
                orderDto.setPostalCode(postalCode);
                orderDto.setPrice(Double.toString(price));
                orderDto.setReferenceTrack1(referenceTrack1);
                orderDto.setReferenceTrack2(referenceTrack2);
                orderDto.setReferenceTrack3(referenceTrack3);
                orderDto.setSampleYoutubeLink(sampleYoutubeLink);
                orderDto.setSongLength(songLength);
                orderDto.setTempoValue(tempoValue);
                orderDto.setComplete(false);

                return orderService.saveOrder(orderDto,sampleLocalFile);
            }
            return responseDto;
        }
    }

    @PostMapping("/saveComment")
    public ResponseDto addComment(@RequestBody CommentDto commentDto) {
        return orderService.saveComment(commentDto);
    }

    @PostMapping("/completeOrder/{id}")
    public ResponseDto completeOrder(@PathVariable("id")long id) {
        return orderService.completeOrder(id);
    }

    @GetMapping("/findAllComplete/{pageNumber}/{rowsPerPage}")
    public ResponseDto findAllCompleteOrders(@PathVariable("pageNumber")int pageNumber, @PathVariable("rowsPerPage")int rowsPerPage) {
        return orderService.findAllCompleteOrders(pageNumber,rowsPerPage);
    }

    @GetMapping("/findAllCompleteByEmail/{email:.+}/{pageNumber}/{rowsPerPage}")
    public ResponseDto findAllCompleteOrdersByEmail(@PathVariable("pageNumber")int pageNumber, @PathVariable("rowsPerPage")int rowsPerPage, @PathVariable("email")String email) {
        return orderService.findAllCompleteOrdersByEmail(pageNumber,rowsPerPage,email);
    }

    @GetMapping("/findOrderById/{id}")
    public ResponseDto findOrderById(@PathVariable("id")long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping("/findOrders")
    public ResponseDto findOrders(@RequestBody JsonNode jsonNode) {

        int pageNumber = jsonNode.get("pageNumber").asInt();
        int rowsPerPage = jsonNode.get("rowsPerPage").asInt();

        String type = null;
        if (jsonNode.get("type") != null && !jsonNode.get("type").asText().equals("null")) {
            type = jsonNode.get("type").asText();
        }

        String searchKeyWord;
        if (jsonNode.get("keyword") != null && !jsonNode.get("keyword").asText().equals("null")) {
            searchKeyWord = jsonNode.get("keyword").asText();
            searchKeyWord = "%" + searchKeyWord + "%";
        } else {
            searchKeyWord = "%%";
        }

        return orderService.findOrders(pageNumber,rowsPerPage,type,searchKeyWord);
    }

    @GetMapping("/findCompleteOrders/{keyword:.+}/{pageNumber}/{rowsPerPage}")
    public ResponseDto findCompleteOrders(@PathVariable("keyword") String searchKeyword,@PathVariable("pageNumber") int pageNumber,@PathVariable("rowsPerPage") int rowsPerPage) {

        if (searchKeyword != null && !searchKeyword.equals("null")) {
            searchKeyword = "%" + searchKeyword + "%";
        } else {
            searchKeyword = "%%";
        }

        return orderService.findOrders(pageNumber,rowsPerPage,"COMPLETE",searchKeyword);
    }
}
