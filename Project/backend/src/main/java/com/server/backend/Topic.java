package com.server.backend;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdByUser_id")
	private User createdBy;

	private String name;

	private String description;

	@Column(nullable = true)
	private String location;

	private boolean isHighPriority;

	public int getId() {
		return this.id;
	}

	public User getUser() {
		return this.createdBy;
	}

	public void setUser(User u) {
		this.createdBy = u;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String l) {
		this.location = l;
	}

	public boolean highPriority() {
		return this.isHighPriority;
	}

	public void setHighPriority(boolean p) {
		this.isHighPriority = p;
	}
}
