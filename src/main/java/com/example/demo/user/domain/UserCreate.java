package com.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private final String email;
    private final String nickname;
    private final String address;

    @Builder
    public UserCreate(String email, String nickname, String address) {
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }
}
