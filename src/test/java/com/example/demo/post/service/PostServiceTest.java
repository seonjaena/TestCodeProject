package com.example.demo.post.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.infrastructure.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "classpath:sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById_를_이용하여_게시물_정보를_얻어올_수_있다() {
        // given
        // when
        Post result = postService.getById(1);

        // then
        assertThat(result.getId()).isEqualTo(1);
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
                .writerId(1)
                .content(content)
                .build();

        // when
        Post result = postService.create(postCreate);

        // then
        assertThat(result.getId()).isEqualTo(2);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getWriter().getId()).isEqualTo(1);
//        assertThat(result.getCreatedAt()).isEqualTo("T.T");
        assertThat(result.getModifiedAt()).isNull();
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
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getWriter().getId()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(content);
//        assertThat(result.getModifiedAt()).isEqualTo("T.T");
    }

}
