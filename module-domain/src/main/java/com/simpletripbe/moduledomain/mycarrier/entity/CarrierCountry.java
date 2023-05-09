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
public class CarrierCountry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrier_country_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id")
    private MyCarrier myCarrier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_name")
    private Country country;

}
