package com.example.demo.user.controller;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.request.UserUpdateRequest;
import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserControllerTest {

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된_채_전달_받을_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .lastLoginAt(100L)
                .build());


        // when
        ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().email()).isEqualTo("test@test.com");
        assertThat(result.getBody().nickname()).isEqualTo("sjna");
        assertThat(result.getBody().lastLoginAt()).isEqualTo(100);
        assertThat(result.getBody().status()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용가_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.userController.getUserById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .lastLoginAt(100L)
                .build());


        // when
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).status()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .lastLoginAt(100L)
                .build());


        // when
        assertThatThrownBy(() -> {
            testContainer.userController.verifyEmail(1, "aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaab");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1679530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .lastLoginAt(100L)
                .build());


        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("test@test.com");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().email()).isEqualTo("test@test.com");
        assertThat(result.getBody().nickname()).isEqualTo("sjna");
        assertThat(result.getBody().address()).isEqualTo("Seoul");
        assertThat(result.getBody().lastLoginAt()).isEqualTo(1679530673958L);
        assertThat(result.getBody().status()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .lastLoginAt(100L)
                .build());


        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("test@test.com", UserUpdateRequest.builder()
                .address("Pangyo")
                .nickname("sjna-m")
                .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().email()).isEqualTo("test@test.com");
        assertThat(result.getBody().nickname()).isEqualTo("sjna-m");
        assertThat(result.getBody().lastLoginAt()).isEqualTo(100);
        assertThat(result.getBody().address()).isEqualTo("Pangyo");
        assertThat(result.getBody().status()).isEqualTo(UserStatus.ACTIVE);
    }

}
