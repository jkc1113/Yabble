package com.server.backend;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_id")
	private User follower;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "followee_id")
	private User followee;
	
	public Integer getId() {
		return id;
	}
	
	public User getFollower() {
		return follower;
	}
	
	public void setFollower(User follower) {
		this.follower = follower;
	}
	
	public User getFollowee() {
		return followee;
	}
	
	public void setFollowee(User followee) {
		this.followee = followee;
	}
}
