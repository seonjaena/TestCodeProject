package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    void UserCreate_객체로_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("test@test.com")
                .address("Seoul")
                .nickname("test")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getAddress()).isEqualTo("Seoul");
        assertThat(user.getNickname()).isEqualTo("test");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa");
    }

    @Test
    void UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaa")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .address("Busan")
                .nickname("test")
                .build();

        // when
        user = user.update(userUpdate);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getAddress()).isEqualTo("Busan");
        assertThat(user.getNickname()).isEqualTo("test");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaa");
    }

    @Test
    void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaa")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void 우효한_인증_코드로_계정을_활성화_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaa")
                .build();

        // when
        user = user.certificate("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaa");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaa")
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            user.certificate("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaab");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}
