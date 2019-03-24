package com.server.backend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.backend.Follow;
import com.server.backend.FollowRepository;
import com.server.backend.User;

@Service
public class FollowService {
	
	@Autowired
	private FollowRepository followRepo;
	
	public List<User> getFollowers(Integer userId){
		ArrayList<User> followers = new ArrayList<>();
		for(Follow f: followRepo.findFollowers(userId)) {
				followers.add(f.getFollower());
		}
		return followers;
	}
	
	public List<User> getFollowing(Integer userId){
		ArrayList<User> following = new ArrayList<>();
		for(Follow f: followRepo.findFollowing(userId)) {
				following.add(f.getFollowee());
		}
		return following;
	}
	
}
