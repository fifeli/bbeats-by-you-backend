package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="BEAT_PRICE")
public class BeatPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "START_TIME_SECONDS")
    private Integer startTimeSeconds;

    @Column(name = "END_TIME_SECONDS")
    private Integer endTimeSeconds;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "UOM")
    private String uom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}
