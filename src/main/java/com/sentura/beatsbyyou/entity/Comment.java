package com.sentura.beatsbyyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMMENT",length = 500)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "FK_ORDER")
    private Order fkOrder;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}
