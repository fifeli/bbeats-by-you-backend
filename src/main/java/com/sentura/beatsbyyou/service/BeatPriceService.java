package com.sentura.beatsbyyou.service;

import com.sentura.beatsbyyou.dto.BeatPriceDto;
import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.entity.BeatPrice;
import com.sentura.beatsbyyou.repository.BeatPriceRepository;
import com.sentura.beatsbyyou.util.ObjectEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeatPriceService {

    @Autowired
    private BeatPriceRepository beatPriceRepository;

    private Logger logger = LoggerFactory.getLogger(BeatPriceService.class);

    public ResponseDto saveOrUpdate(BeatPriceDto beatPriceDto) {

        try{
            if (beatPriceDto.getPrice() == null) {
                throw new Exception("price can not be null");
            } else {

                BeatPrice existingBeatPrice = beatPriceRepository.findBeatPriceByStartAndEndTimes(beatPriceDto.getStartTimeSeconds(),beatPriceDto.getEndTimeSeconds());
                if (existingBeatPrice != null) {
                    throw new Exception("You already have given time ranges!");
                } else {
                    BeatPrice beatPrice = beatPriceRepository.saveAndFlush(new ObjectEntityConverter().beatPriceDtoToEntity(beatPriceDto));
                    beatPriceDto.setId(beatPrice.getId());
                }
            }
            return new ResponseDto("success",beatPriceDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto delete(long id) {

        try{
            BeatPrice beatPrice = beatPriceRepository.findFirstById(id);
            if (beatPrice == null) {
                throw new Exception("beat price not found");
            } else {
                beatPriceRepository.delete(beatPrice);
            }
            return new ResponseDto("success",id);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAll() {

        try{
            List<BeatPriceDto> beatPrices = new ArrayList<>();
            List<BeatPrice> beatPriceList = beatPriceRepository.findAll();
            if (!beatPriceList.isEmpty()) {
                for(BeatPrice beatPrice: beatPriceList) {
                    beatPrices.add(new ObjectEntityConverter().beatPriceEntityToDto(beatPrice));
                }
            }
            return new ResponseDto("success",beatPrices);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getPriceByTimeRange(int beatTimeSeconds) {

        try{
            BeatPrice beatPrice = beatPriceRepository.findBeatPriceByTimeRange(beatTimeSeconds);
            BeatPriceDto beatPriceDto;
            if (beatPrice != null) {
                beatPriceDto = new ObjectEntityConverter().beatPriceEntityToDto(beatPrice);
            } else {
                throw new Exception("beat prices not found in given range");
            }
            return new ResponseDto("success",beatPriceDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }
}
