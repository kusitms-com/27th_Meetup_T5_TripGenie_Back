package com.simpletripbe.moduledomain.mycarrier.mapper;

import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.TicketMemoRes;
import com.simpletripbe.moduledomain.mycarrier.entity.TicketMemo;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = TicketMemo.class
)
public interface TicketMemoMapper {

    @Mappings({
            @Mapping(source = "ticket", target = "ticket"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "content", target = "content"),
            @Mapping(target = "deleteYn", expression = "java(\"N\")")
    })
    TicketMemo toTicketMemoEntity(TicketMemoDTO ticketMemoDTO);

    @Mappings({
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "content", target = "content")
    })
    TicketMemoRes toTicketMemoRes(TicketMemo ticketMemo);
}
