package com.simpletripbe.moduledomain.login.mapper;

import com.simpletripbe.moduledomain.login.dto.UserDetailDTO;
import com.simpletripbe.moduledomain.login.entity.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    @Mappings({
            @Mapping(source = "pictureUrl", target = "picture")
    })
    User toEntity(UserDetailDTO userDetailDTO);

}
