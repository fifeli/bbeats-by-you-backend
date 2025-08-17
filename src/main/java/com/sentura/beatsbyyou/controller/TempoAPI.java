package com.sentura.beatsbyyou.controller;

import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.service.TempoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/tempo")
public class TempoAPI {

    @Autowired
    private TempoService tempoService;

    @PostMapping("/upload")
    public ResponseDto uploadTempo(
            @RequestParam("musicTrack") MultipartFile musicTrack,
            @RequestParam("musicTrackThumbnail") MultipartFile musicTrackThumbnail,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tempoValue") Integer tempoValue,
            @RequestParam(value = "id",required = false) Long id
    ) {

        return tempoService.saveTempo(musicTrack,musicTrackThumbnail,id,title,description,tempoValue);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteTempo(@PathVariable("id") long id) {
        return tempoService.deleteTempo(id);
    }

    @GetMapping("/getAll")
    public ResponseDto getAllTempo() {
        return tempoService.getAllTempo();
    }

    @GetMapping("/getAllByPagination/{pageNumber}/{rowsPerPage}")
    public ResponseDto getAllTempoByPagination(@PathVariable("pageNumber")int pageNumber,@PathVariable("rowsPerPage")int rowsPerPage) {
        return tempoService.getAllTempoByPagination(pageNumber, rowsPerPage);
    }

    @GetMapping("/getAllByValue/{value}")
    public ResponseDto getAllTempoByValue(@PathVariable("value") int value) {
        return tempoService.getAllTempoByValue(value);
    }

    @GetMapping("/getAllByValueAndPagination/{pageNumber}/{rowsPerPage}/{value}")
    public ResponseDto getAllTempoByValueAndPagination(@PathVariable("pageNumber") int pageNumber,@PathVariable("rowsPerPage") int rowsPerPage,@PathVariable("value") int value) {
        return tempoService.getAllTempoByValueAndPagination(pageNumber,rowsPerPage,value);
    }
}
