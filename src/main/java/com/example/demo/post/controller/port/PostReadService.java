package com.example.demo.post.controller.port;

import com.example.demo.post.domain.Post;

public interface PostReadService {

    Post getById(long id);

}
