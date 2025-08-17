package com.sentura.beatsbyyou.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeatPriceDto {

    private long id;
    private int startTimeSeconds;
    private int endTimeSeconds;
    private String price;
    private String uom;
}
