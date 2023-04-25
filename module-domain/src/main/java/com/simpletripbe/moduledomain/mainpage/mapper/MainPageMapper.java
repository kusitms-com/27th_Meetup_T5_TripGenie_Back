package com.simpletripbe.moduledomain.mainpage.mapper;

import com.simpletripbe.moduledomain.mainpage.dto.MainPageListDTO;
import com.simpletripbe.moduledomain.mainpage.entity.MainPage;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = MainPage.class
)
public interface MainPageMapper {

    @Mappings({
            @Mapping(target = "continent", ignore = true),
            @Mapping(target = "country", ignore = true),
            @Mapping(target = "image", ignore = true),
    })
    List<MainPageListDTO> toDTO(List<MainPage> communityList);

}
