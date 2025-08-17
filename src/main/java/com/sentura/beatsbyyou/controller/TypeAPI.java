package com.sentura.beatsbyyou.controller;

import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.dto.TypeDto;
import com.sentura.beatsbyyou.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/types")
public class TypeAPI {

    @Autowired
    private TypeService typeService;

    @PostMapping("/saveMood")
    public ResponseDto saveMood(@RequestBody TypeDto typeDto) {
        return typeService.saveMood(typeDto);
    }

    @PostMapping("/saveGenre")
    public ResponseDto saveGenre(@RequestBody TypeDto typeDto) {
        return typeService.saveGenre(typeDto);
    }

    @PostMapping("/saveInstrument")
    public ResponseDto saveInstrument(@RequestBody TypeDto typeDto) {
        return typeService.saveInstrument(typeDto);
    }

    @PostMapping("/deleteMood/{id}")
    public ResponseDto deactivateMood(@PathVariable long id) {
        return typeService.deactivateMood(id);
    }

    @PostMapping("/deleteInstrument/{id}")
    public ResponseDto deactivateInstrument(@PathVariable long id) {
        return typeService.deactivateInstrument(id);
    }

    @PostMapping("/deleteGenre/{id}")
    public ResponseDto deactivateGenre(@PathVariable long id) {
        return typeService.deactivateGenre(id);
    }

    @GetMapping("/getAllMoods")
    public ResponseDto getAllMoods() {
        return typeService.getAllMoods();
    }

    @GetMapping("/getAllInstruments")
    public ResponseDto getAllInstruments() {
        return typeService.getAllInstruments();
    }

    @GetMapping("/getAllGenres")
    public ResponseDto getAllGenres() {
        return typeService.getAllGenres();
    }
}
