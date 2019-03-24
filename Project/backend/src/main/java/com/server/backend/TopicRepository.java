package com.server.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
	List<Topic> findByCategoryOrderByTimestampDesc(Category category);
}
