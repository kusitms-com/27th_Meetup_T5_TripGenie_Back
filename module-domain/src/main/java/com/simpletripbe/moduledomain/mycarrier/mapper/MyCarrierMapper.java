package com.simpletripbe.moduledomain.mycarrier.mapper;

import com.simpletripbe.moduledomain.batch.dto.MyBagSaveDTO;
import com.simpletripbe.moduledomain.mycarrier.dto.CarrierListDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierCountry;
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
    })
    List<CarrierListDTO> toCarrierDto(List<CarrierCountry> carrierCountries);

    @Mappings({
    })
    MyCarrier toEntity(CarrierListDTO carrierListDTO);

    @Mappings({
    })
    MyCarrier toMyBagEntity(MyBagSaveDTO myBagSaveDTO);

}
