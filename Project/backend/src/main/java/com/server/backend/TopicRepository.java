package com.server.backend;

import org.springframework.data.repository.CrudRepository;

import com.server.backend.Topic;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

}
