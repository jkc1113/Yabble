package com.server.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer>{

	@Query(value="SELECT * FROM FOLLOW WHERE FOLLOWEE_ID = ?0", nativeQuery = true)
	List<Follow> findFollowers(Integer userId);
	
	@Query(value="SELECT * FROM FOLLOW WHERE FOLLOWER_ID = ?0", nativeQuery = true)
	List<Follow> findFollowing(Integer userId);
	
}
