package com.sentura.beatsbyyou.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {

    private String desc;
    private String code = "200";
    private String timestamp = new Date().toString();
    private Object result;

    public ResponseDto(String desc, Object result) {
        this.desc = desc;
        this.result = result;
    }

    public ResponseDto(String desc, String code, Object result) {
        this.desc = desc;
        this.code = code;
        this.result = result;
    }
}
