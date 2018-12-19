package com.server.backend;

import org.springframework.data.repository.CrudRepository;

import com.server.backend.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {

}