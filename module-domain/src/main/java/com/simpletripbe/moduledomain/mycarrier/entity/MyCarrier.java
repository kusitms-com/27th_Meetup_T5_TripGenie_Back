package com.simpletripbe.moduledomain.mycarrier.entity;

import com.simpletripbe.moduledomain.basetime.BaseTimeEntity;
import com.simpletripbe.moduledomain.login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MyCarrier extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrier_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "email", nullable = false)
    @MapsId("id")
    private User user;

    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    private String deleteYn;

    @Enumerated(value = EnumType.STRING)
    private CarrierType type;

    @OneToMany(mappedBy = "myCarrier")
    private List<Ticket> tickets = new ArrayList<>();

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void updateType(CarrierType carrierType) {
        this.type = carrierType;
    }

}
