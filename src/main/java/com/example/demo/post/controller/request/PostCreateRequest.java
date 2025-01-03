package com.example.demo.post.controller.request;

import com.example.demo.post.domain.PostCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    private final long writerId;
    private final String content;

    @Builder
    public PostCreateRequest(
            @JsonProperty("writerId") long writerId,
            @JsonProperty("content") String content) {
        this.writerId = writerId;
        this.content = content;
    }

    public PostCreate toModel() {
        return PostCreate.builder()
                .writerId(this.writerId)
                .content(this.content)
                .build();
    }

}
