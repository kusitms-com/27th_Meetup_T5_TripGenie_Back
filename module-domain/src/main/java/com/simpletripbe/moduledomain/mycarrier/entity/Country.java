package com.simpletripbe.moduledomain.mycarrier.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {

    @Id
    @Column(name = "country_name")
    public String name;
    private String continent;
    private String image;
    private LocalDateTime createDate;
    private LocalDateTime updDate;

    public String getName() {
        return this.name;
    }

}
