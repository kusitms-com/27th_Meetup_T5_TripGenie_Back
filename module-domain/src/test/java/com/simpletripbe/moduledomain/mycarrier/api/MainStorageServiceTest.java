package com.simpletripbe.moduledomain.mycarrier.api;

import com.simpletripbe.moduledomain.login.entity.User;
import com.simpletripbe.moduledomain.login.repository.UserRepository;
import com.simpletripbe.moduledomain.mycarrier.dto.StorageDTO;
import com.simpletripbe.moduledomain.mycarrier.entity.CarrierType;
import com.simpletripbe.moduledomain.mycarrier.entity.MyCarrier;
import com.simpletripbe.moduledomain.mycarrier.repository.MyCarrierRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class MainStorageServiceTest {

    @Autowired
    MainStorageService mainStorageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyCarrierRepository myCarrierRepository;

    @Test
    void selectAll() {

        //given
        User user = createUser("a");
        userRepository.save(user);

        // 캐리어 5개
        for (Long i = 1L; i <= 5; i++) {
            MyCarrier carrier = createMyCarrier(user, i, CarrierType.CARRIER);
            myCarrierRepository.save(carrier);
        }

        // 보관함 캐리어 10개
        for (Long i = 6L; i <= 15; i++) {
            MyCarrier storage = createMyCarrier(user, i, CarrierType.STORAGE);
            myCarrierRepository.save(storage);
        }

        //when
        List<StorageDTO> results = mainStorageService.selectAll(user.getEmail());

        //then
        Assertions.assertThat(results.size()).isEqualTo(10);

    }

    private User createUser(String email) {
        return User.builder()
                .email(email)
                .name("asdf")
                .nickname("qwer")
                .roles("ROLE_USER")
                .deleteYn("N")
                .build();
    }

    private MyCarrier createMyCarrier(User user, Long id, CarrierType carrierType) {
        return MyCarrier.builder()
                .id(id)
                .user(user)
                .name("일본여행캐리어")
                .startDate(LocalDate.of(2023, 5, 19))
                .endDate(LocalDate.of(2023, 5, 23))
                .deleteYn("N")
                .type(carrierType)
                .build();
    }
}