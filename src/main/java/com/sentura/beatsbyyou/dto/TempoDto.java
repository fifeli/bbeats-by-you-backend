package com.sentura.beatsbyyou.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TempoDto {

    private long id;
    private String title;
    private String musicTrackUrl;
    private String musicTrackThumbnailUrl;
    private int tempoValue;
    private String description;
}
