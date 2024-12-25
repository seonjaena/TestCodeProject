package com.example.demo.controller;

import com.example.demo.model.dto.PostUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "classpath:sql/post-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 게시물을_아이디를_이용하여_찾아올_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("hello-world"))
                .andExpect(jsonPath("$.writer.id").value(1));
    }

    @Test
    void 존재하지_않는_아이디를_이용하여_게시물을_찾으면_404_응답을_받는다() throws Exception {
        mockMvc.perform(get("/api/posts/99999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Posts에서 ID 99999999를 찾을 수 없습니다."));
    }

    @Test
    void 게시물을_수정할_수_있다() throws Exception {
        // given
        String content = "hello-world-modified";
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content(content)
                .build();

        // when
        // then
        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.writer.id").value(1));
    }

    @Test
    void 존재하지_않는_게시물을_수정할_수_없다() throws Exception {
        // given
        String content = "hello-world-modified";
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content(content)
                .build();

        // when
        // then
        mockMvc.perform(put("/api/posts/99999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Posts에서 ID 99999999를 찾을 수 없습니다."));
    }

}
