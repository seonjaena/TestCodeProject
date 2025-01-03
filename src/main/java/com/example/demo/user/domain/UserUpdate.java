package com.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdate {

    private final String nickname;
    private final String address;

    @Builder
    public UserUpdate(String nickname, String address) {
        this.nickname = nickname;
        this.address = address;
    }
}
