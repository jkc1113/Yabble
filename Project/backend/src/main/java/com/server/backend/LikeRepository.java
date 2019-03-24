package com.server.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer>{
	List<Like> findByUserId(Integer userId);
}
