package com.infiniteink.services.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.infiniteink.entities.Post;
import com.infiniteink.exceptions.PostNotFoundException;
import com.infiniteink.repositories.PostRepo;
import com.infiniteink.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postrepo;

	@Override
	public List<Post> getAllPosts() {
		return postrepo.findAllActivePosts();
	}

	@Override
	public Post getPostbyId(Long Id) {
		// TODO Auto-generated method stub
		return postrepo.findActivePostById(Id);
	}

	@Override
	public List<Post> getPostByUserId(Long userId) {
		// TODO Auto-generated method stub
		return postrepo.findActivePostsByUserId(userId);
	}

	@Override
	public List<Post> getPostByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		return postrepo.findActivePostsByCategoryId(categoryId);
	}

	@Override
	public Post createPost(Post post) {
		// TODO Auto-generated method stub
		return postrepo.save(post);
	}

	@Override
	public Post updatePost(Long id, Post postDto) {
		Post post = postrepo.findActivePostById(id);
		if (post == null) {
			throw new PostNotFoundException("Post not found with id " + id);
		}
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());

		return postrepo.save(post);
	}

	@Override
	public void deletePost(Long id) {
		postrepo.softDeletePostById(id);
	}

}
