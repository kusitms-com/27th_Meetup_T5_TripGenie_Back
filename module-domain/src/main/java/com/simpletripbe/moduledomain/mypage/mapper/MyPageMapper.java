package com.simpletripbe.moduledomain.mypage.mapper;

import com.simpletripbe.moduledomain.community.dto.InfoDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageStampListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = MyPage.class
)
public interface MyPageMapper {

    MyPageProfileListDTO toProfileDTO(MyPage myPage);

    MyPageDocumentListDTO toDocumentDTO(MyPage myPage);

    MyPageStampListDTO toStampDTO(MyPage myPage);

}
