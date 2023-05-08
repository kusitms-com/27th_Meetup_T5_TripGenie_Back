package com.simpletripbe.moduledomain.mycarrier.entity;

import com.simpletripbe.moduledomain.login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCarrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mycarrier_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String email;
    private String deleteYn;
    private CarrierType type;
    private LocalDateTime creDate;
    private LocalDateTime updDate;

}
