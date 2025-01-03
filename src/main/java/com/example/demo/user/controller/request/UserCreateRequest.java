package com.example.demo.user.controller.request;

import com.example.demo.user.domain.UserCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    private final String email;
    private final String nickname;
    private final String address;

    @Builder
    public UserCreateRequest(
        @JsonProperty("email") String email,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("address") String address) {
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }

    public UserCreate toModel() {
        return UserCreate.builder()
                .email(this.email)
                .nickname(this.nickname)
                .address(this.address)
                .build();
    }

}
