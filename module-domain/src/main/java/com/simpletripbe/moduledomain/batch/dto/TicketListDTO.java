package com.simpletripbe.moduledomain.batch.dto;

import com.simpletripbe.moduledomain.login.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketListDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
    private User user;

    public TicketListDTO(LocalDate startDate, LocalDate endDate, String name, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.user = user;
    }

}
