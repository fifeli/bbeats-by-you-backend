package com.sentura.beatsbyyou.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private long id;
    private List<TypeDto> moods;
    private List<TypeDto> instruments;
    private List<TypeDto> genres;
    private List<CommentDto> comments;
    private String fullName;
    private String email;
    private String mobile;
    private String postalCode;
    private String address;
    private String songLength;
    private String description;
    private String sampleYoutubeLink;
    private String sampleLocalFile;
    private String referenceTrack1;
    private String referenceTrack2;
    private String referenceTrack3;
    private int tempoValue;
    private String price;
    private String dateTime;
    private boolean complete;
}
