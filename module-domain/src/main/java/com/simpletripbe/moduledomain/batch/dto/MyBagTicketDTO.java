package com.simpletripbe.moduledomain.batch.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.simpletripbe.moduledomain.mycarrier.entity.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MyBagTicketDTO {
    private Long id;

    private TicketType type;
    private String ticketUrl;
    private String imageUrl;
    private String title;
    private Integer sequence;
    private LocalDate endDate;

    @QueryProjection
    public MyBagTicketDTO(Long id, TicketType type, String ticketUrl, String imageUrl, String title, Integer sequence, LocalDate endDate) {
        this.id = id;
        this.type = type;
        this.ticketUrl = ticketUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.sequence = sequence;
        this.endDate = endDate;
    }
}