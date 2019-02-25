package com.server.backend.FrontEndObjects;

import java.io.Serializable;

import com.server.backend.Category;
import com.server.backend.Topic;

public class FrontEndTopic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4025205509987327342L;
	public int creatorId;
	public int id;
	public String description;
	public boolean isHighPriority;
	public String location;
	public String name;
	public Category category;

	public FrontEndTopic() {
	}

	public FrontEndTopic(Topic e) {
		try {
			this.creatorId = e.getUser().getId();
		} catch (NullPointerException p) {
			this.creatorId = -1;
		}

		try {
			this.id = e.getId();
		} catch (NullPointerException p) {
			this.id = -1;
		}

		try {
			this.description = e.getDescription();
		} catch (NullPointerException p) {
			this.description = null;
		}

		try {
			this.isHighPriority = e.highPriority();
		} catch (NullPointerException p) {
			this.isHighPriority = false;
		}

		try {
			this.location = e.getLocation();
		} catch (NullPointerException p) {
			this.location = null;
		}

		try {
			this.name = e.getName();
		} catch (NullPointerException p) {
			this.name = null;
		}
		
		try {
			this.category = e.getCategory();
		} catch (NullPointerException p) {
			this.category = null;
		}
	}
}
