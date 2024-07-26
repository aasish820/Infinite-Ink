package com.infiniteink.services;

import java.util.List;

import com.infiniteink.entities.Post;
public interface PostService  {
	//to view a list of all posts in the system.
	//To View Post by ID
	//View All posts by User ID
	//View All posts by Category ID
	//to add a new post
	//to update the existing post
	// to delete the existing post
	
	List<Post> getAllPosts();
	
	Post getPostbyId(Long id);
	
	List<Post>getPostByUserId(Long userId);
	
	List<Post>getPostByCategoryId(Long categoryId);
	
	Post createPost(Post post);
	
	Post updatePost(Long id,Post postDto);
	
	void deletePost(Long id);
}
