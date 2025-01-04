package com.example.demo.post.controller.request;

import com.example.demo.post.domain.PostUpdate;
import lombok.Builder;

@Builder
public record PostUpdateRequest(String content) {

    public PostUpdate toModel() {
        return PostUpdate.builder()
                .content(this.content)
                .build();
    }

}
