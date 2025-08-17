package com.sentura.beatsbyyou.service;

import com.sentura.beatsbyyou.dto.PaginationDto;
import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.dto.TempoDto;
import com.sentura.beatsbyyou.entity.Tempo;
import com.sentura.beatsbyyou.repository.TempoRepository;
import com.sentura.beatsbyyou.util.FileUtilizer;
import com.sentura.beatsbyyou.util.ObjectEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class TempoService {

    @Autowired
    private TempoRepository tempoRepository;

    private Logger logger = LoggerFactory.getLogger(TempoService.class);

    @Value("${archive.path}")
    private String archivePath;

    @Value("${protocol}")
    private String protocol;

    @Value("${host}")
    private String host;

    @Value("${server.port}")
    private String port;

    public ResponseDto saveTempo(MultipartFile musicTrackFile,MultipartFile musicTrackThumbFile,Long id,String title,String description,Integer tempoValue) {

        try{
            String urlPrefix = protocol + "://" + host + "/api/files/";
            String trackName = new FileUtilizer().generateFileName(musicTrackFile.getOriginalFilename());
            String thumbName = new FileUtilizer().generateFileName(musicTrackThumbFile.getOriginalFilename());

            if (!new FileUtilizer().writeToDisk(musicTrackFile, Paths.get(archivePath), trackName)) {
                throw new Exception("File Writing Error Occured!");
            }
            if (!new FileUtilizer().writeToDisk(musicTrackThumbFile, Paths.get(archivePath), thumbName)) {
                throw new Exception("File Writing Error Occured!");
            }

            TempoDto tempoDto = new TempoDto();
            if (id != null) tempoDto.setId(id);
            tempoDto.setTempoValue(tempoValue);
            tempoDto.setTitle(title);
            tempoDto.setDescription(description);
            tempoDto.setMusicTrackUrl(urlPrefix + trackName);
            tempoDto.setMusicTrackThumbnailUrl(urlPrefix + thumbName);

            Tempo tempo = new ObjectEntityConverter().tempoDtoToEntity(tempoDto);
            tempo.setThumbnailLocation(archivePath + "/" + thumbName);
            tempo.setTrackLocation(archivePath + "/" + trackName);

            tempo = tempoRepository.saveAndFlush(tempo);

            tempoDto.setId(tempo.getId());

            return new ResponseDto("success",tempoDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto deleteTempo(long id) {
        try{
            Tempo tempo = tempoRepository.findFirstById(id);
            if (tempo != null) {
                tempoRepository.delete(tempo);
            }else{
                throw new Exception("tempo not found");
            }
            return new ResponseDto("success",tempo.getId());
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllTempo() {
        try{
            List<TempoDto> tempos = new ArrayList<>();
            List<Tempo> tempoList = tempoRepository.findAll();
            if (!tempoList.isEmpty()) {
                for(Tempo tempo: tempoList) {
                    tempos.add(new ObjectEntityConverter().tempoEntityToDto(tempo));
                }
            }
            return new ResponseDto("success",tempos);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllTempoByPagination(int pageNumber, int rowsPerPage) {
        try{
            Page<Tempo> pagedTempos = tempoRepository.findAll(PageRequest.of(pageNumber,rowsPerPage));
            PaginationDto paginationDto;
            List<TempoDto> tempoList;
            if (pagedTempos != null) {

                tempoList = new ArrayList<>();

                pagedTempos.toList().forEach(tempo->tempoList.add(new ObjectEntityConverter().tempoEntityToDto(tempo)));

                paginationDto = new PaginationDto();
                paginationDto.setPageNumber(pageNumber);
                paginationDto.setRowsPerPage(rowsPerPage);
                paginationDto.setTotalPages(pagedTempos.getTotalPages());
                paginationDto.setPagedData(tempoList);

            } else {
                throw new Exception("Tempo Beats not found");
            }

            return new ResponseDto("success",paginationDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllTempoByValue(int value) {
        try{
            List<TempoDto> tempos = new ArrayList<>();
            List<Tempo> tempoList = tempoRepository.findAllByTempoValue(value);
            if (!tempoList.isEmpty()) {
                for(Tempo tempo: tempoList) {
                    tempos.add(new ObjectEntityConverter().tempoEntityToDto(tempo));
                }
            }
            return new ResponseDto("success",tempos);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllTempoByValueAndPagination(int pageNumber, int rowsPerPage, int value) {
        try{
            Page<Tempo> pagedTempos = tempoRepository.findAllByTempoValue(value,PageRequest.of(pageNumber,rowsPerPage));
            PaginationDto paginationDto;
            List<TempoDto> tempoList;
            if (pagedTempos != null) {

                tempoList = new ArrayList<>();

                pagedTempos.toList().forEach(tempo->tempoList.add(new ObjectEntityConverter().tempoEntityToDto(tempo)));

                paginationDto = new PaginationDto();
                paginationDto.setPageNumber(pageNumber);
                paginationDto.setRowsPerPage(rowsPerPage);
                paginationDto.setTotalPages(pagedTempos.getTotalPages());
                paginationDto.setPagedData(tempoList);

            } else {
                throw new Exception("Tempo Beats not found");
            }

            return new ResponseDto("success",paginationDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }
}
