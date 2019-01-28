package com.server.backend;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Topic topic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String comment;

	private Timestamp time;

	private boolean isPrivate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_id")
	private Post parent;

	public Integer getId() {
		return this.id;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic e) {
		this.topic = e;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User u) {
		this.user = u;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String c) {
		this.comment = c;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp t) {
		this.time = t;
	}

	public boolean isPrivate() {
		return this.isPrivate;
	}

	public void setPrivate(boolean p) {
		this.isPrivate = p;
	}

	public Post getParent() {
		return this.parent;
	}
	
	public void setParent(Post e) {
		this.parent = e;
	}
	
	public boolean isDescendentOf(Post p) {
		if(parent == null)
			return false;
		if(parent.id.equals(p.id))
			return true;
		return parent.isDescendentOf(p);
	}
}
