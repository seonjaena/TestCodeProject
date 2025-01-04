package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserResponseTest {

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
        MyProfileResponse userResponse = MyProfileResponse.from(user);

        // then
        assertThat(userResponse.id()).isEqualTo(1L);
        assertThat(userResponse.email()).isEqualTo("test@test.com");
        assertThat(userResponse.nickname()).isEqualTo("test");
        assertThat(userResponse.address()).isEqualTo("Seoul");
        assertThat(userResponse.status()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.lastLoginAt()).isEqualTo(100L);
    }

}
