package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Mood> moods;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Instrument> instruments;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Genre> genres;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "fkOrder")
    private List<Comment> comments;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "SONG_LENGTH")
    private String songLength;

    @Column(name = "DESCRIPTION",length = 60000)
    private String description;

    @Column(name = "SAMPLE_YOUTUBE_LINK")
    private String sampleYoutubeLink;

    @Column(name = "SAMPLE_LOCAL_FILE_URL")
    private String sampleLocalFileUrl;

    @Column(name = "SAMPLE_LOCAL_FILE_LOCATION")
    private String sampleLocalFileLocation;

    @Column(name = "REFFERENCE_TRACK_1")
    private String referenceTrack1;

    @Column(name = "REFFERENCE_TRACK_2")
    private String referenceTrack2;

    @Column(name = "REFFERENCE_TRACK_3")
    private String referenceTrack3;

    @Column(name = "TEMPO_VALUE")
    private Integer tempoValue;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "COMPLETE",columnDefinition = "boolean default true")
    private Boolean complete;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}
