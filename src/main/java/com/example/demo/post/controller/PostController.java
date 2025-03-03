package com.example.demo.post.controller;

import com.example.demo.post.controller.port.PostReadService;
import com.example.demo.post.controller.port.PostUpdateService;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.post.controller.response.PostResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@Builder
@RequiredArgsConstructor
public class PostController {

    private final PostReadService postReadService;
    private final PostUpdateService postUpdateService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(PostResponse.from(postReadService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        return ResponseEntity
            .ok()
            .body(PostResponse.from(postUpdateService.update(id, postUpdateRequest.toModel())));
    }
}