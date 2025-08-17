package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="EMAIL_HISTORY")
public class EmailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FK_ORDER")
    private Order fkOrder;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "EMAIL_TO")
    private String emailTo;

    @Column(name = "EMAIL_CC")
    private String emailCc;

    @Column(name = "MESSAGE",length = 60000)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}
