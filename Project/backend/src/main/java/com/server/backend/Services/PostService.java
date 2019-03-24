package com.server.backend.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.backend.Like;
import com.server.backend.LikeRepository;
import com.server.backend.Post;
import com.server.backend.PostRepository;
import com.server.backend.Share;
import com.server.backend.ShareRepository;
import com.server.backend.User;

@Service
public class PostService {

	@Autowired
	FollowService followService;
	
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	ShareRepository shareRepo;
	
	@Autowired
	LikeRepository likeRepo;
	
	public List<Post> getPostsOnTopic(int topicId) {
		List<Post> ret = new ArrayList<>();

		Iterable<Post> posts = this.postRepo.findAll();
		for (Post p : posts) {
			if (p.getTopic().getId().intValue() == topicId) {
				ret.add(p);
			}
		}

		return ret;
	}
	
	public List<Post> getPostsByUser(int userId){
		List<Post> ret = new ArrayList<>();

		Iterable<Post> posts = this.postRepo.findAll();
		for (Post e : posts) {
			if (e.getUser().getId().intValue() == userId)
				ret.add(e);
		}
		Iterable<Share> shares = this.shareRepo.findAll();
		for (Share s : shares) {
			if (s.getUser().getId().intValue() == userId)
				ret.add(s.getPost());
		}

		return ret;
	}
	
	public List<Post> getPostsByFollowing(int userId){
		List<User> following = followService.getFollowing(userId);
		ArrayList<Post> posts = new ArrayList<>();
		for(User u: following) {
			for(Post p: getPostsByUser(u.getId().intValue())){
				posts.add(p);
			}
		}
		return posts;
	}

	public List<Post> getLikedPosts(Integer id) {
		List<Like> likes = likeRepo.findByUserId(id);
		ArrayList<Post> posts = new ArrayList<>();
		for(Like l: likes) {
			posts.add(l.getPost());
		}
		return posts;
	}
	
}
