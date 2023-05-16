package com.simpletripbe.moduledomain.community.mapper;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.community.entity.Community;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CommunityMapper {

    // 조건이 여러 개라면 @Mappings로 묶어서 안에 Mapping을 세팅해준다.
     InfoDTO toDTO(Community community);

}
