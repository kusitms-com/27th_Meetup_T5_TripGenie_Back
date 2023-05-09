package com.simpletripbe.moduledomain.mycarrier.entity;

import com.simpletripbe.moduledomain.basetime.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country extends BaseTimeEntity {

    @Id
    @Column(name = "country_name")
    public String name;

    private String continent;
    private String image;

    public String getName() {
        return this.name;
    }

}
