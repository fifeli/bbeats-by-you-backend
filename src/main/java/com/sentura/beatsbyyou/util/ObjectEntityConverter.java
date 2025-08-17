package com.sentura.beatsbyyou.util;

import com.sentura.beatsbyyou.dto.*;
import com.sentura.beatsbyyou.entity.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ObjectEntityConverter {

    private DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public UserDto userEntityToDto(User user){

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setId(user.getId());
        userDto.setActive(user.getActive());

        return userDto;
    }

    public User userDtoToEntity(UserDto userDto){

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setId(userDto.getId());
        user.setActive(userDto.isActive());
        if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());

        return user;
    }

    public TypeDto moodEntityToDto(Mood mood){

        TypeDto typeDto = new TypeDto();
        typeDto.setActive(mood.getActive());
        typeDto.setId(mood.getId());
        typeDto.setName(mood.getName());

        return typeDto;
    }

    public TypeDto genreEntityToDto(Genre genre){

        TypeDto typeDto = new TypeDto();
        typeDto.setActive(genre.getActive());
        typeDto.setId(genre.getId());
        typeDto.setName(genre.getName());

        return typeDto;
    }

    public TypeDto instrumentEntityToDto(Instrument instrument){

        TypeDto typeDto = new TypeDto();
        typeDto.setActive(instrument.getActive());
        typeDto.setId(instrument.getId());
        typeDto.setName(instrument.getName());

        return typeDto;
    }

    public Mood typeDtoToMoodEntity(TypeDto typeDto){

        Mood mood = new Mood();
        mood.setActive(typeDto.isActive());
        mood.setName(typeDto.getName());
        mood.setId(typeDto.getId());

        return mood;
    }

    public Genre typeDtoToGenreEntity(TypeDto typeDto){

        Genre genre = new Genre();
        genre.setActive(typeDto.isActive());
        genre.setName(typeDto.getName());
        genre.setId(typeDto.getId());

        return genre;
    }

    public Instrument typeDtoToInstrumentEntity(TypeDto typeDto){

        Instrument instrument = new Instrument();
        instrument.setActive(typeDto.isActive());
        instrument.setName(typeDto.getName());
        instrument.setId(typeDto.getId());

        return instrument;
    }

    public TempoDto tempoEntityToDto(Tempo tempo){

        TempoDto tempoDto = new TempoDto();
        tempoDto.setDescription(tempo.getDescription());
        tempoDto.setId(tempo.getId());
        tempoDto.setMusicTrackThumbnailUrl(tempo.getThumbnailUrl());
        tempoDto.setMusicTrackUrl(tempo.getTrackUrl());
        tempoDto.setTitle(tempo.getTitle());
        tempoDto.setTempoValue(tempo.getTempoValue());

        return tempoDto;
    }

    public Tempo tempoDtoToEntity(TempoDto tempoDto){

        Tempo tempo = new Tempo();
        tempo.setTitle(tempoDto.getTitle());
        tempo.setDescription(tempoDto.getDescription());
        tempo.setId(tempoDto.getId());
        tempo.setTempoValue(tempoDto.getTempoValue());
        tempo.setThumbnailUrl(tempoDto.getMusicTrackThumbnailUrl());
        tempo.setTrackUrl(tempoDto.getMusicTrackUrl());

        return tempo;
    }

    public BeatPriceDto beatPriceEntityToDto(BeatPrice beatPrice){

        BeatPriceDto beatPriceDto = new BeatPriceDto();
        beatPriceDto.setId(beatPrice.getId());
        beatPriceDto.setEndTimeSeconds(beatPrice.getEndTimeSeconds());
        beatPriceDto.setStartTimeSeconds(beatPrice.getStartTimeSeconds());
        beatPriceDto.setPrice(decimalFormat.format(beatPrice.getPrice()));
        beatPriceDto.setUom(beatPrice.getUom());

        return beatPriceDto;
    }

    public BeatPrice beatPriceDtoToEntity(BeatPriceDto beatPriceDto){

        BeatPrice beatPrice = new BeatPrice();
        beatPrice.setId(beatPriceDto.getId());
        beatPrice.setEndTimeSeconds(beatPriceDto.getEndTimeSeconds());
        beatPrice.setStartTimeSeconds(beatPriceDto.getStartTimeSeconds());
        beatPrice.setPrice(Double.parseDouble(beatPriceDto.getPrice()));
        beatPrice.setUom(beatPriceDto.getUom());

        return beatPrice;
    }

    public CommentDto commentEntityToDto(Comment comment){

        CommentDto commentDto = new CommentDto();
        commentDto.setComment(comment.getComment());
        commentDto.setId(comment.getId());
        commentDto.setDateTime(dateFormat.format(comment.getCreateDateTime()));

        return commentDto;
    }

    public Comment commentDtoToEntity(CommentDto commentDto){

        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setId(commentDto.getId());

        return comment;
    }

    public OrderDto orderEntityToDto(Order order){

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTempoValue(order.getTempoValue());
        orderDto.setSongLength(order.getSongLength());
        orderDto.setSampleYoutubeLink(order.getSampleYoutubeLink());
        orderDto.setReferenceTrack1(order.getReferenceTrack1());
        orderDto.setReferenceTrack2(order.getReferenceTrack2());
        orderDto.setReferenceTrack3(order.getReferenceTrack3());
        orderDto.setPrice(decimalFormat.format(order.getPrice()));
        orderDto.setPostalCode(order.getPostalCode());
        if (order.getCreateDateTime() != null) {
            orderDto.setDateTime(dateFormat.format(order.getCreateDateTime()));
        }

        List<TypeDto> moods = new ArrayList<>();
        List<TypeDto> instruments = new ArrayList<>();
        List<TypeDto> genres = new ArrayList<>();
        List<CommentDto> comments = new ArrayList<>();

        if (order.getMoods() != null && !order.getMoods().isEmpty()) order.getMoods().forEach(mood->moods.add(moodEntityToDto(mood)));
        if (order.getInstruments() != null && !order.getInstruments().isEmpty()) order.getInstruments().forEach(instrument->instruments.add(instrumentEntityToDto(instrument)));
        if (order.getGenres() != null && !order.getGenres().isEmpty()) order.getGenres().forEach(genre->genres.add(genreEntityToDto(genre)));
        if (order.getComments() != null && !order.getComments().isEmpty()) order.getComments().forEach(comment->comments.add(commentEntityToDto(comment)));

        orderDto.setMoods(moods);
        orderDto.setInstruments(instruments);
        orderDto.setGenres(genres);
        orderDto.setComments(comments);

        orderDto.setReferenceTrack1(order.getReferenceTrack1());
        orderDto.setReferenceTrack2(order.getReferenceTrack2());
        orderDto.setReferenceTrack3(order.getReferenceTrack3());
        orderDto.setSampleYoutubeLink(order.getSampleYoutubeLink());
        orderDto.setSongLength(order.getSongLength());
        orderDto.setTempoValue(order.getTempoValue());
        orderDto.setMobile(order.getMobile());
        orderDto.setFullName(order.getFullName());
        orderDto.setEmail(order.getEmail());
        orderDto.setDescription(order.getDescription());
        orderDto.setComplete(order.getComplete());
        orderDto.setAddress(order.getAddress());
        orderDto.setSampleLocalFile(order.getSampleLocalFileUrl());

        return orderDto;
    }

    public Order orderDtoToEntity(OrderDto orderDto){

        Order order = new Order();
        try{
            order.setId(orderDto.getId());
            order.setAddress(orderDto.getAddress());
            order.setComplete(orderDto.isComplete());
            order.setDescription(orderDto.getDescription());
            order.setEmail(orderDto.getEmail());
            order.setFullName(orderDto.getFullName());

            List<Genre> genres = new ArrayList<>();
            List<Instrument> instruments = new ArrayList<>();
            List<Mood> moods = new ArrayList<>();

            if(orderDto.getGenres() != null && !orderDto.getGenres().isEmpty()) orderDto.getGenres().forEach(genre->genres.add(typeDtoToGenreEntity(genre)));
            if(orderDto.getMoods() != null && !orderDto.getMoods().isEmpty()) orderDto.getMoods().forEach(mood->moods.add(typeDtoToMoodEntity(mood)));
            if(orderDto.getInstruments() != null && !orderDto.getInstruments().isEmpty()) orderDto.getInstruments().forEach(instrument->instruments.add(typeDtoToInstrumentEntity(instrument)));

            order.setGenres(genres);
            order.setInstruments(instruments);
            order.setMoods(moods);
            order.setMobile(orderDto.getMobile());
            order.setPostalCode(orderDto.getPostalCode());
            order.setTempoValue(orderDto.getTempoValue());
            order.setSongLength(orderDto.getSongLength());
            order.setSampleYoutubeLink(orderDto.getSampleYoutubeLink());
            order.setReferenceTrack1(orderDto.getReferenceTrack1());
            order.setReferenceTrack2(orderDto.getReferenceTrack2());
            order.setReferenceTrack3(orderDto.getReferenceTrack3());
            order.setPrice(decimalFormat.parse(orderDto.getPrice()).doubleValue());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }
}
