package com.server.backend.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.server.backend.Follow;
import com.server.backend.FollowRepository;
import com.server.backend.User;

import FrontEndObjects.FrontEndUser;

@Service
public class FollowService {
	
	@Autowired
	private FollowRepository followRepo;
	
	public List<FrontEndUser> getFollowers(User u){
		ArrayList<FrontEndUser> followers = new ArrayList<>();
		if(u == null)
			return followers;
		for(Follow f: followRepo.findAll()) {
			if(f.getFollowee().getId().equals(u.getId()))
				followers.add(new FrontEndUser(f.getFollower()));
		}
		return followers;
	}
	
	public List<FrontEndUser> getFollowing(User u){
		ArrayList<FrontEndUser> following = new ArrayList<>();
		if(u == null)
			return following;
		for(Follow f: followRepo.findAll()) {
			if(f.getFollower().getId().equals(u.getId()))
				following.add(new FrontEndUser(f.getFollowee()));
		}
		return following;
	}
	
}
