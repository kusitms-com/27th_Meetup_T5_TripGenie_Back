package com.simpletripbe.moduledomain.batch.entity;

import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.mycarrier.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long alarmId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

    private String message;
    private LocalDateTime creDate;
    private LocalDateTime updDate;

}