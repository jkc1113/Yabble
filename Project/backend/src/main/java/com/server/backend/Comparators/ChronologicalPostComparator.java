package com.server.backend.Comparators;

import java.util.Comparator;

import com.server.backend.Post;

public class ChronologicalPostComparator implements Comparator<Post>{

	@Override
	public int compare(Post p1, Post p2) {
		return p2.getTime().compareTo(p1.getTime());
	}

}
