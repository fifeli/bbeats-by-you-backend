package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="EMAIL_TEMPLATE")
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL_TYPE")
    private String emailType;

    @Column(name = "EMAIL_FROM")
    private String emailFrom;

    @Column(name = "TEMPLATE",length = 60000)
    private String template;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "ACTIVE",columnDefinition = "boolean default true")
    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}
