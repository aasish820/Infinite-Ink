package com.infiniteink.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infiniteink.entities.Category;
import com.infiniteink.entities.Post;
import com.infiniteink.entities.User;
import com.infiniteink.exceptions.PostNotFoundException;
import com.infiniteink.models.PostResponse;
import com.infiniteink.models.UserDTO;
import com.infiniteink.services.impl.CategoryServiceImpl;
import com.infiniteink.services.impl.PostServiceImpl;
import com.infiniteink.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	@GetMapping("/fetch")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

	@GetMapping("/fetch/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Post post = postService.getPostbyId(id);
        if (post == null) {
            throw new PostNotFoundException("Post not found with id " + id);
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/fetch/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable("userId") Long userId) {
        return postService.getPostByUserId(userId);
    }

    @GetMapping("/fetch/category/{categoryId}")
    public List<Post> getPostsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return postService.getPostByCategoryId(categoryId);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@ModelAttribute("postresponse") PostResponse postResponse) {
    	Category category = categoryService.viewCategoryByID(postResponse.getCategory_id());
    	UserDTO userDTO = userService.getUserByID(postResponse.getUser_id());
    	System.out.println(userDTO);
    	Post post = new Post();
    	post.setContent(postResponse.getContent());
    	post.setTitle(postResponse.getTitle());
    	post.setCategory(category);
    	
    	User user = new User();
    	user.setId(userDTO.getId());
    	user.setFull_name(userDTO.getFull_name());
    	user.setAddress(userDTO.getAddress());
    	user.setEmail(userDTO.getEmail());
    	user.setPassword(userDTO.getPassword());
    	user.setAbout(userDTO.getAbout());
    	
    	post.setUser(user);
    	
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDto) {
        Post updatedPost = postService.updatePost(id, postDto);
        if (updatedPost == null) {
            throw new PostNotFoundException("Post not found with id " + id);
        }
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
	


