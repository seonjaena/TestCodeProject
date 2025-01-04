package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MyProfileResponseTest {

    @Test
    void User로_응답을_생성할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .build();

        // when
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then
        assertThat(myProfileResponse.id()).isEqualTo(1);
        assertThat(myProfileResponse.email()).isEqualTo("test@test.com");
        assertThat(myProfileResponse.address()).isEqualTo("Seoul");
        assertThat(myProfileResponse.status()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.lastLoginAt()).isEqualTo(100L);
        assertThat(user.certificationCode()).isEqualTo("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa");
    }

}
