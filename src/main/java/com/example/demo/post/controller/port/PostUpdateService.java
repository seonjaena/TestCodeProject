package com.example.demo.post.controller.port;

import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;

public interface PostUpdateService {

    Post update(long id, PostUpdate postUpdate);

}
