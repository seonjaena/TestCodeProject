package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

public class PostCreateControllerTest {

    @Test
    void 게시물을_작성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1679530673958L))
                .build();

        User writer = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .lastLoginAt(100L)
                .build());

        testContainer.postRepository.save(Post.builder()
                .content("hello-world")
                .writer(writer)
                .build());

        // when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.createPost(PostCreate.builder()
                .content("hello-world")
                .writerId(1)
                .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody().getId()).isEqualTo(2);
        assertThat(result.getBody().getContent()).isEqualTo("hello-world");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1679530673958L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
    }

    @Test
    void PENDING_상태의_사용자는_게시물을_작성할_수_없다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(1679530673958L))
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
        // then
        assertThatThrownBy(() -> {
            testContainer.postCreateController.createPost(PostCreate.builder()
                    .content("hello-world")
                    .writerId(1L)
                    .build());
        }).isInstanceOf(ResourceNotFoundException.class);
    }

}
