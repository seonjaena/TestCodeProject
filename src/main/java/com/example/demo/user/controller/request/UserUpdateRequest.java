package com.example.demo.user.controller.request;

import com.example.demo.user.domain.UserUpdate;
import lombok.Builder;

@Builder
public record UserUpdateRequest(String nickname, String address) {

    public UserUpdate toModel() {
        return UserUpdate.builder()
                .nickname(this.nickname)
                .address(this.address)
                .build();
    }

}
