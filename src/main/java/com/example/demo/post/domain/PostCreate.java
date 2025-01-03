package com.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {

    private final long writerId;
    private final String content;

    @Builder
    public PostCreate(long writerId, String content) {
        this.writerId = writerId;
        this.content = content;
    }
}
