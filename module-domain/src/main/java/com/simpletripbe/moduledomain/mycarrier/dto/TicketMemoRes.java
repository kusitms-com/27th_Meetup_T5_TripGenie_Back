package com.simpletripbe.moduledomain.mycarrier.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketMemoRes {

    private String imageUrl;
    private String content;

    public TicketMemoRes(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
