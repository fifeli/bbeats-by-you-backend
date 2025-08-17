package com.sentura.beatsbyyou.controller;

import com.sentura.beatsbyyou.dto.BeatPriceDto;
import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.service.BeatPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("beatPrice")
public class BeatPriceAPI {

    @Autowired
    private BeatPriceService beatPriceService;

    @PostMapping("/saveOrUpdate")
    public ResponseDto saveOrUpdate(@RequestBody BeatPriceDto beatPriceDto) {

        return beatPriceService.saveOrUpdate(beatPriceDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") long id) {

        return beatPriceService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseDto getAll() {

        return beatPriceService.getAll();
    }

    @GetMapping("/getPriceByTimeRange/{beatTimeSeconds}")
    public ResponseDto getPriceByTimeRange(@PathVariable("beatTimeSeconds") int beatTimeSeconds) {

        return beatPriceService.getPriceByTimeRange(beatTimeSeconds);
    }
}
