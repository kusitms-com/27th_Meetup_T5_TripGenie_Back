package com.simpletripbe.moduledomain.mycarrier.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrier_id")
    private MyCarrier carrierId;
    @Column(name = "ticket_url")
    private String ticketUrl;
    @Column(name = "image_url")
    private String imageUrl;
    private TicketType type;
    private String title;
    private Integer sequence;
    private LocalDateTime createDate;
    private LocalDateTime updDate;

}
