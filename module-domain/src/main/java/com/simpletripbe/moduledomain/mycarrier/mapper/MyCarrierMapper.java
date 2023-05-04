package com.simpletripbe.moduledomain.mycarrier.mapper;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
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
            @Mapping(source = "country", target = "country", ignore = true),
            @Mapping(source = "image", target = "image", ignore = true),
            @Mapping(source = "file", target = "file", ignore = true),
            @Mapping(source = "link", target = "link", ignore = true)
    })
    List<CarrierListDTO> toCarrierDto(List<MyCarrier> myCarrier);

    @Mappings({
            @Mapping(source = "country", target = "country", ignore = true),
            @Mapping(source = "image", target = "image", ignore = true),
            @Mapping(source = "file", target = "file", ignore = true),
            @Mapping(source = "link", target = "link", ignore = true)
    })
    MyCarrier toEntity(CarrierListDTO carrierListDTO);

}
