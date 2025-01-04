package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String email, String nickname, UserStatus status, Long lastLoginAt) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.id())
                .email(user.email())
                .nickname(user.nickname())
                .status(user.status())
                .lastLoginAt(user.lastLoginAt())
                .build();
    }

}
