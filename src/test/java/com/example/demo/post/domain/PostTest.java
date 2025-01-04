package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

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
        assertThat(post.content()).isEqualTo("helloworld");
        assertThat(post.createdAt()).isEqualTo(1679530673958L);
        assertThat(post.writer().email()).isEqualTo("test@test.com");
        assertThat(post.writer().nickname()).isEqualTo("test");
        assertThat(post.writer().address()).isEqualTo("Seoul");
        assertThat(post.writer().status()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.writer().certificationCode()).isEqualTo("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa");
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
        assertThat(post.content()).isEqualTo("foobar");
        assertThat(post.modifiedAt()).isEqualTo(1679530673958L);
    }

}
