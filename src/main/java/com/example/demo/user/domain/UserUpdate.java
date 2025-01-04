package com.example.demo.user.domain;

import lombok.Builder;

@Builder
public record UserUpdate(String nickname, String address) {

}
