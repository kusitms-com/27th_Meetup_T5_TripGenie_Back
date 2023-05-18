package com.simpletripbe.moduledomain.mypage.repository;

import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;

import java.util.List;
import java.util.Optional;

public interface MyPageRepositoryCustom {

    User findProfileByNickname(String nickname);

    String selectMyProfileImageByNickname(String nickname);

    void updateMyNickname(MyPageProfileListDTO listDTO);

    List<MyPage> findDocumentByNickname(String nickname);

    List<MyPage> findStampByNickname(String nickname);

}
