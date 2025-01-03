package com.example.demo.post.controller.request;

import com.example.demo.post.domain.PostUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private final String content;

    @Builder
    public PostUpdateRequest(
            @JsonProperty("content") String content) {
        this.content = content;
    }

    public PostUpdate toModel() {
        return PostUpdate.builder()
                .content(this.content)
                .build();
    }

}
