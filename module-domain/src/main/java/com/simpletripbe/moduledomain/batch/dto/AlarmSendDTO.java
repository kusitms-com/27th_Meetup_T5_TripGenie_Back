package com.simpletripbe.moduledomain.batch.dto;

import com.simpletripbe.moduledomain.login.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlarmSendDTO {

    private String message;
    private String name;
    private User email;
    private LocalDate startDate;
    private LocalDate endDate;

}
