package com.server.backend.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.backend.Follow;
import com.server.backend.FollowRepository;
import com.server.backend.User;
import com.server.backend.UserRepository;
import com.server.backend.FrontEndObjects.FrontEndUser;
import com.server.backend.Services.FollowService;

@Controller
public class FollowController {

	@Autowired
	private FollowService followService;
	
	@Autowired
	private FollowRepository followRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("api/followUser")
	public ResponseEntity followUser(HttpSession session, @RequestParam int followId) {
		Follow f = new Follow();
		User u = (User)session.getAttribute("user");
		if(u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		try {
			f.setFollowee(userRepo.findById(new Integer(followId)).get());
			f.setFollower(u);
			followRepo.save(f);
		}catch(NoSuchElementException e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("api/unfollowUser")
	public ResponseEntity unfollowUser(HttpSession session, @RequestParam int followId) {
		User u = (User)session.getAttribute("user");
		if(u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		for(Follow f: followRepo.findAll()) {
			if(f.getFollowee().getId().intValue() == followId){
				followRepo.delete(f);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("api/getFollowers")
	public List<FrontEndUser> getFollowers(HttpSession session){
		User u = (User)session.getAttribute("user");
		if(u == null)
			return null;
		List<User> followers = followService.getFollowers(u.getId());
		ArrayList<FrontEndUser> ret = new ArrayList<>();
		for(User user: followers)
			ret.add(new FrontEndUser(user));
		return ret;
	}
	
	@GetMapping("api/getFollowing")
	public List<FrontEndUser> getFollowing(HttpSession session){
		User u = (User)session.getAttribute("user");
		if(u == null)
			return null;
		List<User> followers = followService.getFollowing(u.getId());
		ArrayList<FrontEndUser> ret = new ArrayList<>();
		for(User user: followers)
			ret.add(new FrontEndUser(user));
		return ret;
	}
	
}
