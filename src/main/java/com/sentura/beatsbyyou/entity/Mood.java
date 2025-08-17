package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="MOODS")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE",columnDefinition = "boolean default true")
    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}
