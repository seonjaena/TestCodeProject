package com.example.demo.post.controller.port;

import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;

public interface PostCreateService {

    Post create(PostCreate postCreate);

}
