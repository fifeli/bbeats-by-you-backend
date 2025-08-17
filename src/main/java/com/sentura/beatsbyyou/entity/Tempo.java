package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="TEMPO")
public class Tempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION",length = 500)
    private String description;

    @Column(name = "TEMPO_VALUE")
    private Integer tempoValue;

    @Column(name = "TRACK_LOCATION")
    private String trackLocation;

    @Column(name = "TRACK_URL")
    private String trackUrl;

    @Column(name = "THUMBNAIL_LOCATION")
    private String thumbnailLocation;

    @Column(name = "THUMBNAIL_URL")
    private String thumbnailUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();

}
