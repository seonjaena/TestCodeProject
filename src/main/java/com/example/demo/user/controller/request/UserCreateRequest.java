package com.example.demo.user.controller.request;

import com.example.demo.user.domain.UserCreate;
import lombok.Builder;

@Builder
public record UserCreateRequest(String email, String nickname, String address) {

    public UserCreate toModel() {
        return UserCreate.builder()
                .email(this.email)
                .nickname(this.nickname)
                .address(this.address)
                .build();
    }

}
