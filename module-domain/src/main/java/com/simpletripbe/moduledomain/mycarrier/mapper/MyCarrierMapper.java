package com.simpletripbe.moduledomain.mycarrier.mapper;

import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.*;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierCountry;
import com.simpletripbe.moduledomain.mycarrier.entity.Country;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.entity.Ticket;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = MyCarrier.class
)
public interface MyCarrierMapper {

    @Mappings({
            @Mapping(source = "name", target = "name", ignore = true),
            @Mapping(source = "email", target = "email", ignore = true),
            @Mapping(source = "startDate", target = "startDate", ignore = true),
            @Mapping(source = "endDate", target = "endDate", ignore = true)
    })
    List<TicketTypeDTO> toTicketTypeDto(List<Ticket> tickets);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "email", target = "user.email"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(target = "deleteYn", expression = "java(\"N\")"),
//            @Mapping(target = "creDate", expression = "java(java.time.LocalDateTime.now())")
    })
    MyCarrier toCarrierEntity(CarrierListDTO carrierListDTO);

    @Mappings({
            @Mapping(source = "country", target = "name")
    })
    Country toCountryEntity(CarrierListDTO carrierListDTO);

    @Mappings({
            @Mapping(source = "country", target = "country", ignore = true),
//            @Mapping(source = "", target = "myCarrier.id"),
//            @Mapping(target = "creDate", expression = "java(java.time.LocalDateTime.now())")
    })
    CarrierCountry toCarrierCountryEntity(CarrierListDTO carrierListDTO);

    @Mappings({
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "url", target = "ticketUrl"),
            @Mapping(source = "myCarrier", target = "myCarrier"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "sequence", target = "sequence"),
            @Mapping(target = "deleteYn", expression = "java(\"N\")"),
            @Mapping(source = "id", target = "id", ignore = true)
    })
    Ticket toTicketEntity(TicketUrlDTO ticketUrlDTO);

    @Mappings({
            @Mapping(source = "type", target = "type", ignore = true),
//            @Mapping(source = "updDate", target = "updDate", ignore = true)
    })
    MyCarrier toMyBagEntity(MyBagSaveDTO myBagSaveDTO);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "ticketUrl", target = "ticketUrl")
    })
    List<TicketDTO> toTicketDTO(List<Ticket> tickets);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate")
    })
    CarrierInfoRes toCarrierInfoRes(MyCarrier myCarrier);

}
