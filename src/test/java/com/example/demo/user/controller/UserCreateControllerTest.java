package com.example.demo.user.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.controller.request.UserCreateRequest;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa"))
                .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(UserCreateRequest.builder()
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().email()).isEqualTo("test@test.com");
        assertThat(result.getBody().nickname()).isEqualTo("sjna");
        assertThat(result.getBody().lastLoginAt()).isNull();
        assertThat(result.getBody().status()).isEqualTo(UserStatus.PENDING);
        assertThat(testContainer.userRepository.getById(1).certificationCode()).isEqualTo("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa");
    }

}
