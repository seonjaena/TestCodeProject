package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PostResponseTest {

    @Test
    void Post로_응답을_생성할_수_있다() {
        // given
        User writer = User.builder()
                .email("test@test.com")
                .nickname("test")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa")
                .build();

        Post post = Post.builder()
                .content("helloworld")
                .writer(writer)
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.content()).isEqualTo("helloworld");
        assertThat(postResponse.writer().email()).isEqualTo("test@test.com");
        assertThat(postResponse.writer().nickname()).isEqualTo("test");
        assertThat(postResponse.writer().status()).isEqualTo(UserStatus.ACTIVE);
    }

}
