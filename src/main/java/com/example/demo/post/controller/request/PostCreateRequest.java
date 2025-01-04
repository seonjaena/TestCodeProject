package com.example.demo.post.controller.request;

import com.example.demo.post.domain.PostCreate;
import lombok.Builder;

@Builder
public record PostCreateRequest(long writerId, String content) {

    public PostCreate toModel() {
        return PostCreate.builder()
                .writerId(this.writerId)
                .content(this.content)
                .build();
    }

}
