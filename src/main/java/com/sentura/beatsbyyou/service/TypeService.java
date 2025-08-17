package com.sentura.beatsbyyou.service;

import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.dto.TypeDto;
import com.sentura.beatsbyyou.entity.Genre;
import com.sentura.beatsbyyou.entity.Instrument;
import com.sentura.beatsbyyou.entity.Mood;
import com.sentura.beatsbyyou.repository.GenreRepository;
import com.sentura.beatsbyyou.repository.InstrumentRepository;
import com.sentura.beatsbyyou.repository.MoodRepository;
import com.sentura.beatsbyyou.util.ObjectEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    private Logger logger = LoggerFactory.getLogger(TypeService.class);

    public ResponseDto saveMood(TypeDto typeDto) {

        try{
            typeDto.setActive(true);
            Mood mood = moodRepository.saveAndFlush(new ObjectEntityConverter().typeDtoToMoodEntity(typeDto));
            typeDto.setId(mood.getId());
            return new ResponseDto("success",typeDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto saveGenre(TypeDto typeDto) {

        try{
            typeDto.setActive(true);
            Genre genre = genreRepository.saveAndFlush(new ObjectEntityConverter().typeDtoToGenreEntity(typeDto));
            typeDto.setId(genre.getId());
            return new ResponseDto("success",typeDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto saveInstrument(TypeDto typeDto) {

        try{
            typeDto.setActive(true);
            Instrument instrument = instrumentRepository.saveAndFlush(new ObjectEntityConverter().typeDtoToInstrumentEntity(typeDto));
            typeDto.setId(instrument.getId());
            return new ResponseDto("success",typeDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto deactivateMood(long id) {

        try{
            Mood mood = moodRepository.findFirstById(id);
            if (mood != null) {
                mood.setActive(false);
                moodRepository.save(mood);
            } else {
                throw new Exception("Mood not found");
            }
            return new ResponseDto("success",id);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto deactivateGenre(long id) {

        try{
            Genre genre = genreRepository.findFirstById(id);
            if (genre != null) {
                genre.setActive(false);
                genreRepository.save(genre);
            } else {
                throw new Exception("Genre not found");
            }
            return new ResponseDto("success",id);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto deactivateInstrument(long id) {

        try{
            Instrument instrument = instrumentRepository.findFirstById(id);
            if (instrument != null) {
                instrument.setActive(false);
                instrumentRepository.save(instrument);
            } else {
                throw new Exception("instrument not found");
            }
            return new ResponseDto("success",id);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllMoods() {

        try{
            List<TypeDto> types = new ArrayList<>();
            List<Mood> moodList = moodRepository.findAllByActiveIsTrue();
            if (!moodList.isEmpty()) {
                for(Mood mood: moodList) {
                    types.add(new ObjectEntityConverter().moodEntityToDto(mood));
                }
            }
            return new ResponseDto("success",types);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllGenres() {

        try{
            List<TypeDto> types = new ArrayList<>();
            List<Genre> genreList = genreRepository.findAllByActiveIsTrue();
            if (!genreList.isEmpty()) {
                for(Genre genre: genreList) {
                    types.add(new ObjectEntityConverter().genreEntityToDto(genre));
                }
            }
            return new ResponseDto("success",types);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAllInstruments() {

        try{
            List<TypeDto> types = new ArrayList<>();
            List<Instrument> instrumentList = instrumentRepository.findAllByActiveIsTrue();
            if (!instrumentList.isEmpty()) {
                for(Instrument instrument: instrumentList) {
                    types.add(new ObjectEntityConverter().instrumentEntityToDto(instrument));
                }
            }
            return new ResponseDto("success",types);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }
}
