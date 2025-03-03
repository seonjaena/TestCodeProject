package com.example.demo.post.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.*;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostServiceTest {

    private PostServiceImpl postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        this.postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1679530673958L))
                .build();

        User user1 = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("sjna")
                .address("Seoul")
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("test2@test.com")
                .nickname("sjna2")
                .address("Seoul")
                .certificationCode("aaaaaaaaaaaaa-aaaaaaaa-aaaaaaa-aaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();

        Post post1 = Post.builder()
                .id(1L)
                .content("hello-world")
                .createdAt(1678530673958L)
                .modifiedAt(1678530673958L)
                .writer(user1)
                .build();

        fakeUserRepository.save(user1);

        fakeUserRepository.save(user2);

        fakePostRepository.save(post1);

    }

    @Test
    void getById_를_이용하여_게시물_정보를_얻어올_수_있다() {
        // given
        // when
        Post result = postService.getById(1);

        // then
        assertThat(result.id()).isEqualTo(1);
    }

    @Test
    void getById_를_이용하여_잘못된_아이디를_넘기면_에러가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            postService.getById(999999999);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
        // given
        String content = "create-test";

        PostCreate postCreate = PostCreate.builder()
                .content(content)
                .writerId(1)
                .build();

        // when
        Post result = postService.create(postCreate);

        // then
        assertThat(result.id()).isNotNull();
        assertThat(result.content()).isEqualTo(content);
        assertThat(result.writer().id()).isEqualTo(1);
        assertThat(result.createdAt()).isEqualTo(1679530673958L);
        assertThat(result.modifiedAt()).isNull();
    }

    @Test
    void postUpdateDto_를_이용하여_게시물을_수정할_수_있다() {
        // given
        String content = "update-test";
        PostUpdate postUpdate = PostUpdate.builder()
                .content(content)
                .build();

        // when
        Post result = postService.update(1, postUpdate);

        // then
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.writer().id()).isEqualTo(1);
        assertThat(result.content()).isEqualTo(content);
        assertThat(result.modifiedAt()).isEqualTo(1679530673958L);
    }

}
