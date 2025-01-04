package com.example.demo.post.controller;

import com.example.demo.post.controller.port.PostCreateService;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.response.PostResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@Builder
@RequiredArgsConstructor
public class PostCreateController {

    private final PostCreateService postCreateService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(PostResponse.from(postCreateService.create(postCreateRequest.toModel())));
    }
}