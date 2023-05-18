package com.simpletripbe.moduledomain.mypage.repository;

import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.mypage.dto.MyPageDocumentListDTO;
import com.simpletripbe.moduledomain.mypage.dto.MyPageProfileListDTO;
import com.simpletripbe.moduledomain.mypage.entity.MyPage;

import java.util.List;

public interface MyPageRepositoryCustom {

    User findProfileByNickname(String nickname);

    void updateMyNickname(MyPageProfileListDTO listDTO);

    List<MyPage> findDocumentByNickname(String nickname);

    List<MyPage> findStampByNickname(String nickname);

}
