package com.server.backend.Controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.server.backend.Like;
import com.server.backend.LikeRepository;
import com.server.backend.Post;
import com.server.backend.PostRepository;
import com.server.backend.Share;
import com.server.backend.ShareRepository;
import com.server.backend.TopicRepository;
import com.server.backend.User;

import FrontEndObjects.FrontEndPost;

public class PostController {

	@Autowired
	PostRepository postRepo;

	@Autowired
	TopicRepository topicRepo;

	@Autowired
	LikeRepository likeRepo;
	
	@Autowired
	ShareRepository shareRepo;

	/**
	 * Gets comments for an Topic
	 *
	 * @param session Session
	 * @param TopicId ID of Topic to get comments for
	 * @return List of comments for Topic. No particular ordering.
	 */
	@GetMapping("/api/getPosts")
	public @ResponseBody List<FrontEndPost> getPosts(HttpSession session, @RequestParam int TopicId) {
		// TODO: check user permissions with role controller here

		List<FrontEndPost> ret = new ArrayList<>();

		Iterable<Post> posts = this.postRepo.findAll();
		for (Post e : posts) {
			if (e.getTopic().getId().intValue() == TopicId) {
				ret.add(new FrontEndPost(e));
			}
		}

		return ret;
	}

	/**
	 * Post a comment about an Topic
	 *
	 * @param session   Session
	 * @param TopicId   ID of Topic to comment on
	 * @param parentId  Parent of comment (use to create sub-comments)
	 * @param comment   Text of comment
	 * @param isPrivate Specifies if this comment is private
	 * @return HTTP 200 if successful, HTTP 500 otherwise
	 */
	@PostMapping("/api/createPost")
	public ResponseEntity createPost(HttpSession session, @RequestParam int TopicId,
			@RequestParam(required = false, defaultValue = "-1") int parentId, @RequestParam String comment,
			@RequestParam boolean isPrivate) {
		try {

			Post toAdd = new Post();

			toAdd.setComment(comment);
			toAdd.setTopic(this.topicRepo.findById(TopicId).get());
			toAdd.setTime(new Timestamp((new Date()).getTime()));
			toAdd.setPrivate(isPrivate);
			if (parentId != -1) {
				toAdd.setParent(this.postRepo.findById(parentId).get());
			}

			this.postRepo.save(toAdd);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/api/likePost")
	public ResponseEntity likePost(HttpSession session, @RequestParam int postId) {
		User u = (User) session.getAttribute("user");
		//TODO only like if not already liked
		if (u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		try {
			Like like = new Like();

			like.setPost(postRepo.findById(new Integer(postId)).get());
			like.setUser(u);
			likeRepo.save(like);
		} catch (NoSuchElementException e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/api/unlikePost")
	public ResponseEntity unlikePost(HttpSession session, @RequestParam int postId) {
		User u = (User)session.getAttribute("user");
		if(u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		for(Like l: likeRepo.findAll()) {
			if(l.getPost().getId().intValue() == postId){
				likeRepo.delete(l);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/api/sharePost")
	public ResponseEntity sharePost(HttpSession session, @RequestParam int postId) {
		User u = (User) session.getAttribute("user");
		//TODO only share if not already shared
		if (u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		try {
			Share share = new Share();

			share.setPost(postRepo.findById(new Integer(postId)).get());
			share.setUser(u);
			shareRepo.save(share);
		} catch (NoSuchElementException e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/api/unsharePost")
	public ResponseEntity unsharePost(HttpSession session, @RequestParam int postId) {
		User u = (User)session.getAttribute("user");
		if(u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		for(Share s: shareRepo.findAll()) {
			if(s.getPost().getId().intValue() == postId){
				shareRepo.delete(s);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
