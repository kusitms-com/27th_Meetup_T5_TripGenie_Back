package com.simpletripbe.moduledomain.mycarrier.mapper;

import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
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
            @Mapping(target = "id", qualifiedByName = "generateUUID"),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "dbsts", ignore = true),
            @Mapping(source = "country", target = "country", ignore = true),
            @Mapping(source = "image", target = "image", ignore = true),
            @Mapping(source = "file", target = "file", ignore = true),
            @Mapping(source = "link", target = "link", ignore = true)
    })
    MyCarrier toEntity(CarrierListDTO carrierListDTO);

    @Named("generateUUID")
    default String generateUuid(final String nonsense) {
        return UUID.randomUUID().toString();
    }

}
