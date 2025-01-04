package com.example.demo.user.domain;

import lombok.Builder;

@Builder
public record UserCreate(String email, String nickname, String address) {

}
