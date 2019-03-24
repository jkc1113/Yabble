package com.server.backend.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.server.backend.Category;
import com.server.backend.PostRepository;
import com.server.backend.Topic;
import com.server.backend.TopicRepository;
import com.server.backend.User;
import com.server.backend.UserRepository;
import com.server.backend.FrontEndObjects.FrontEndTopic;

/**
 * Handles Topic related requests
 *
 * @author jkcowen, bhendel
 *
 */
@Controller
public class TopicController {

	@Autowired
	TopicRepository topicRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	/**
	 * Adds specified Topic. Id is unnecessary. Returns "success" or "failure."
	 *
	 * @param session No need to provide.
	 * @param fe      The frontend wrapper for the Topic requesting to be created.
	 * @return "success" or "failure"
	 */
	@PostMapping("/api/addTopic")
	public ResponseEntity addTopic(HttpSession session, @RequestBody FrontEndTopic fe) {
		Topic e = new Topic();
		User u = null;
		try {
			u = (User) session.getAttribute("user");
			if (u == null)
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
			if (!u.isModerator())
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		e.setUser(u);

		e.setDescription(fe.description);

		e.setHighPriority(fe.isHighPriority);

		e.setLocation(fe.location);

		e.setName(fe.name);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Returns all Topics in the requested group. Returns empty list of user is not
	 * part of group.
	 *
	 * @param session No need to provide.
	 * @return Topics in the requested group.
	 */
	@GetMapping("/api/getTopics")
	public @ResponseBody List<FrontEndTopic> getTopics(HttpSession session, Category c) {
		Iterable<Topic> topics = topicRepository.findByCategoryOrderByTimestampDesc(c);
		ArrayList<FrontEndTopic> userTopics = new ArrayList<>();
		for (Topic e : topics) {
			userTopics.add(new FrontEndTopic(e));
		}
		return userTopics;

	}

	/**
	 * Delete an Topic by its ID
	 *
	 * @param session Session
	 * @param TopicId Topic ID
	 * @return HTTP 200 if successful, HTTP 500 otherwise
	 */
	@PostMapping("/api/deleteTopic")
	public ResponseEntity deleteTopic(HttpSession session, @RequestParam int TopicId) {
		try {
			if (!((User) session.getAttribute("user")).isModerator()) {
				return new ResponseEntity(HttpStatus.FORBIDDEN);
			}
			topicRepository.deleteById(TopicId);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Update an Topic that already exists
	 *
	 * @param session Session
	 * @param Topic   Front end Topic with updates made
	 * @return HTTP 200 if successful, HTTP 500 otherwise
	 */
	@PostMapping("/api/updateTopic")
	public ResponseEntity updateTopic(HttpSession session, @RequestParam FrontEndTopic Topic) {
		try {
			if (!((User) session.getAttribute("user")).isModerator()) {
				return new ResponseEntity(HttpStatus.FORBIDDEN);
			}

			Topic e = topicRepository.findById(Topic.id).get();

			e.setDescription(Topic.description);
			e.setHighPriority(Topic.isHighPriority);
			e.setLocation(Topic.location);
			e.setName(Topic.name);
			e.setUser((User) session.getAttribute("user"));

			this.topicRepository.save(e);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Returns a list of Topics for a given user id
	 *
	 * @param session Session
	 * @return List of Topics for user with given ID
	 */
	@GetMapping("/api/getTopicsForUser")
	public @ResponseBody List<FrontEndTopic> getTopicsForUser(HttpSession session) {

		/*
		 * Down the road could have some algorithm for getting the topics a user sees
		 */
		
		List<FrontEndTopic> ret = new ArrayList<>();

		Iterable<Topic> topics = this.topicRepository.findAll();
		for (Topic e : topics) {
			ret.add(new FrontEndTopic(e));
		}

		return ret;
	}

	
}
