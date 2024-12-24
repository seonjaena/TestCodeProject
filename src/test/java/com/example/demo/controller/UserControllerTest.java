package com.example.demo.controller;

import com.example.demo.model.UserStatus;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.repository.UserEntity;
import com.example.demo.repository.UserRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "classpath:sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된_채_전달_받을_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nickname").value("sjna"))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void 사용가_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/users/99999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Users에서 ID 99999999를 찾을 수 없습니다."));
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/users/2/verify")
                .queryParam("certificationCode", "aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaab"))
                .andExpect(status().isFound());

//        UserEntity result = userRepository.getReferenceById(2L);
        /**
         * findById를 사용하는 이유: getReferenceById를 사용하여 조회하면 프록시 객체가 리턴됨
         * 테스트하는 단에서 ID 필드를 제외한 데이터 필드를 사용하려고 할 때 영속성 컨텍스트에서 조회함
         * 하지만 테스트 코드가 실행되는 부분은 영속성 컨텍스트 세션의 범위 밖임
         * 따라서 LazyInitializationException이 발생함
         * 예외 내용: 'org.hibernate.LazyInitializationException: could not initialize proxy [com.example.demo.repository.UserEntity#2] - no Session'
          */
        UserEntity result = userRepository.findById(2L).get();
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/users/me")
                        .header("EMAIL", "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nickname").value("sjna"))
                .andExpect(jsonPath("$.address").value("Seoul"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        // given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname("sjna-m")
                .address("PyeongYang")
                .build();

        // when
        // then
        mockMvc.perform(put("/api/users/me")
                        .header("EMAIL", "test@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nickname").value("sjna-m"))
                .andExpect(jsonPath("$.address").value("PyeongYang"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

}
