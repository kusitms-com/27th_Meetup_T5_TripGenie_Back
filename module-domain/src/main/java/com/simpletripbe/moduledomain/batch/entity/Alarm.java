package com.simpletripbe.moduledomain.batch.entity;

import com.simpletripbe.moduledomain.basetime.BaseTimeEntity;
import com.simpletripbe.moduledomain.login.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;

    private String message;

}