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
import com.server.backend.Yabble;
import com.server.backend.YabbleRepository;
import com.server.backend.Comparators.ChronologicalPostComparator;
import com.server.backend.FrontEndObjects.FrontEndPost;
import com.server.backend.FrontEndObjects.FrontEndUser;
import com.server.backend.Services.FollowService;
import com.server.backend.Services.PostService;

public class PostController {

	@Autowired
	FollowService followService;

	@Autowired
	PostRepository postRepo;

	@Autowired
	PostService postService;

	@Autowired
	TopicRepository topicRepo;

	@Autowired
	LikeRepository likeRepo;

	@Autowired
	ShareRepository shareRepo;

	@Autowired
	YabbleRepository yabbleRepo;

	/**
	 * Gets comments for an Topic
	 *
	 * @param session Session
	 * @param TopicId ID of Topic to get comments for
	 * @return List of comments for Topic. No particular ordering.
	 */
	@GetMapping("/api/getPostsOnTopic")
	public @ResponseBody List<FrontEndPost> getPostsOnTopic(HttpSession session, @RequestParam int topicId) {
		List<FrontEndPost> ret = new ArrayList<>();
		List<Post> posts = postService.getPostsOnTopic(topicId);
		posts.sort(new ChronologicalPostComparator());
		for (Post p : posts) {
			ret.add(new FrontEndPost(p));
		}

		return ret;
	}

	/**
	 * Get specific post.
	 * 
	 * @param session
	 * @param postId
	 * @return
	 */
	@GetMapping("/api/getPost")
	public @ResponseBody FrontEndPost getPost(HttpSession session, @RequestParam int postId) {
		Post post = postRepo.findById(new Integer(postId)).get();
		return new FrontEndPost(post);
	}

	/**
	 * Get all posts made by user.
	 * 
	 * @param session
	 * @param userId
	 * @return
	 */
	@GetMapping("/api/getPostsByUser")
	public @ResponseBody List<FrontEndPost> getPostsByUser(HttpSession session, @RequestParam int userId) {
		List<FrontEndPost> ret = new ArrayList<>();

		List<Post> posts = postService.getPostsByUser(userId);
		posts.sort(new ChronologicalPostComparator());
		for (Post p : posts) {
			ret.add(new FrontEndPost(p));
		}

		return ret;
	}

	/**
	 * Gets posts for following feed for user
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/api/getPostsByFollowing")
	public @ResponseBody List<FrontEndPost> getPostsByFollowing(HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			return null;
		List<Post> posts = postService.getPostsByUser(u.getId().intValue());
		posts.sort(new ChronologicalPostComparator());
		List<FrontEndPost> ret = new ArrayList<>();
		for (Post p : posts) {
			ret.add(new FrontEndPost(p));
		}
		return ret;
	}
	
	@GetMapping("/api/getLikedPosts")
	public @ResponseBody List<FrontEndPost> getLikedPosts(HttpSession session){
		User u = (User) session.getAttribute("user");
		if(u == null)
			return null;
		List<Post> posts = postService.getLikedPosts(u.getId());
		posts.sort(new ChronologicalPostComparator());
		List<FrontEndPost> ret = new ArrayList<>();
		for(Post p: posts) {
			ret.add(new FrontEndPost(p));
		}
		return ret;
	}

	/**
	 * Returns number of likes on post
	 * 
	 * @param session
	 * @param postId
	 * @return
	 */
	@GetMapping("/api/getLikesOnPost")
	public @ResponseBody Integer getLikesOnPost(HttpSession session, @RequestParam int postId) {
		Iterable<Like> likes = likeRepo.findAll();
		int numLikes = 0;

		for (Like l : likes) {
			if (l.getPost().getId().intValue() == postId) {
				numLikes++;
			}
		}

		return new Integer(numLikes);
	}

	/**
	 * Returns a list of Users
	 * 
	 * @param session
	 * @param postId
	 * @return
	 */
	@GetMapping("/api/getSharesOnPost")
	public @ResponseBody List<FrontEndUser> getSharesOnPost(HttpSession session, @RequestParam int postId) {
		Iterable<Share> shares = shareRepo.findAll();
		ArrayList<FrontEndUser> sharesOnPost = new ArrayList<>();

		for (Share s : shares) {
			if (s.getPost().getId().intValue() == postId) {
				sharesOnPost.add(new FrontEndUser(s.getUser()));
			}
		}

		return sharesOnPost;
	}

	/**
	 * Returns number of Yabbles on post
	 * 
	 * @param session
	 * @param postId
	 * @return
	 */
	@GetMapping("/api/getYabblesOnPost")
	public @ResponseBody Integer getYabblesOnPost(HttpSession session, @RequestParam int postId) {
		Iterable<Yabble> yabbles = yabbleRepo.findAll();
		int numYabbles = 0;

		for (Yabble y : yabbles) {
			if (y.getPost().getId().intValue() == postId) {
				numYabbles++;
			}
		}

		return new Integer(numYabbles);
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

	@PostMapping("/api/getReplies")
	public @ResponseBody List<FrontEndPost> getReplies(HttpSession session, @RequestParam int postId) {
		ArrayList<FrontEndPost> replies = new ArrayList<>();
		Post post = postRepo.findById(new Integer(postId)).get();
		if (post == null)
			return null;

		for (Post p : postRepo.findAll()) {
			if (p.isDescendentOf(post))
				replies.add(new FrontEndPost(p));
		}

		return replies;
	}

	@PostMapping("/api/likePost")
	public ResponseEntity likePost(HttpSession session, @RequestParam int postId) {
		User u = (User) session.getAttribute("user");
		// TODO only like if not already liked
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
		User u = (User) session.getAttribute("user");
		if (u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		for (Like l : likeRepo.findAll()) {
			if (l.getPost().getId().intValue() == postId) {
				likeRepo.delete(l);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/api/likeYabble")
	public ResponseEntity likeYabble(HttpSession session, @RequestParam int postId) {
		User u = (User) session.getAttribute("user");
		// TODO only like if not already liked
		if (u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		try {
			Yabble yabble = new Yabble();

			yabble.setPost(postRepo.findById(new Integer(postId)).get());
			yabble.setUser(u);
			yabbleRepo.save(yabble);
		} catch (NoSuchElementException e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/api/unlikeYabble")
	public ResponseEntity unlikeYabble(HttpSession session, @RequestParam int postId) {
		User u = (User) session.getAttribute("user");
		if (u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		for (Yabble y : yabbleRepo.findAll()) {
			if (y.getPost().getId().intValue() == postId) {
				yabbleRepo.delete(y);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/api/sharePost")
	public ResponseEntity sharePost(HttpSession session, @RequestParam int postId) {
		User u = (User) session.getAttribute("user");
		// TODO only share if not already shared
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
		User u = (User) session.getAttribute("user");
		if (u == null)
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		for (Share s : shareRepo.findAll()) {
			if (s.getPost().getId().intValue() == postId) {
				shareRepo.delete(s);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
