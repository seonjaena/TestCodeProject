package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class PostTest {

    @Test
    void PostCreate로_게시물을_만들_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        User writer = User.builder()
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .build();

        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1679530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getCreatedAt()).isEqualTo(1679530673958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("test");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa");
    }

    @Test
    void PostUpdate로_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        User writer = User.builder()
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1679530673958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when
        post = post.update(postUpdate, new TestClockHolder(1679530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("foobar");
        assertThat(post.getModifiedAt()).isEqualTo(1679530673958L);
    }

}
