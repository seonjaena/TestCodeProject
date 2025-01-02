package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

public class PostControllerTest {

    @Test
    void 게시물을_아이디를_이용하여_찾아올_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
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
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getContent()).isEqualTo("hello-world");
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_아이디를_이용하여_게시물을_찾으면_404_응답을_받는다() {
        // given
        TestContainer testContainer = TestContainer.builder()
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
        // then
        assertThatThrownBy(() -> {
            testContainer.postController.getPostById(99999999);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 게시물을_수정할_수_있다() {
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
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1, PostUpdate.builder()
                .content("hello-world-modified")
                .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getContent()).isEqualTo("hello-world-modified");
        assertThat(result.getBody().getModifiedAt()).isEqualTo(1679530673958L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_게시물을_수정할_수_없다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.postController.updatePost(999999999, PostUpdate.builder()
                    .content("hello-world-modified")
                    .build());
        }).isInstanceOf(ResourceNotFoundException.class);
    }

}
