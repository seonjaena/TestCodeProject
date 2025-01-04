package com.example.demo.post.domain;

import lombok.Builder;

@Builder
public record PostCreate(long writerId, String content) {

}
