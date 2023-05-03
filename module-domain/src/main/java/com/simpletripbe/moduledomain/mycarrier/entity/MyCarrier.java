package com.simpletripbe.moduledomain.mycarrier.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class MyCarrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mycarrier_id")
    private Long id;
    private String country;
    private String image;
    private String file;
    private String link;
    private Date createDate;
    private String dbsts;

}
