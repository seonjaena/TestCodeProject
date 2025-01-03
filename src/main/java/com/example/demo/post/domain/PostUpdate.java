package com.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdate {

    private final String content;

    @Builder
    public PostUpdate(String content) {
        this.content = content;
    }
}
