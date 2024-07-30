package com.infiniteink.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.infiniteink.entities.Category;
import com.infiniteink.entities.Post;
import com.infiniteink.entities.User;
import com.infiniteink.exceptions.PostNotFoundException;
import com.infiniteink.models.PostDTO;
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

	@PostMapping("/create")
	public ResponseEntity<?> createPost(@ModelAttribute("postresponse") PostResponse postResponse,
			@RequestParam(value = "image", required = false) MultipartFile imageFile) {
		try {

			Category category = categoryService.viewCategoryByID(postResponse.getCategory_id());
			UserDTO userDTO = userService.getUserByID(postResponse.getUser_id());

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

			if (imageFile != null && !imageFile.isEmpty()) {
				String UPLOAD_DIR = "uploads/"; 
				Path filePath = Paths.get(UPLOAD_DIR + imageFile.getOriginalFilename());
				Files.createDirectories(filePath.getParent());
				Files.write(filePath, imageFile.getBytes());

				String imageUrl = "/" + UPLOAD_DIR + imageFile.getOriginalFilename();
				post.setImage(imageUrl);
			}

			Post createdPost = postService.createPost(post);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Post created successfully");
			

			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, String> response = new HashMap<>();
			response.put("message", "Failed to upload image and create Post");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

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

	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, String>> updatePost(@PathVariable Long id, @ModelAttribute("postdto") PostDTO postDto) {
		Post updatePost = postService.getPostbyId(id);
		if (updatePost == null) {
			throw new PostNotFoundException("Post not found with id " + id);
		}
		updatePost.setTitle(postDto.getTitle());
		updatePost.setContent(postDto.getContent());
		
		Post updatedPost = postService.updatePost(id, updatePost);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Post updated successfully");
		

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		Post post = postService.getPostbyId(id);
		if (post == null) {
			throw new PostNotFoundException("Post not found with id " + id);
		}
		else {
			postService.deletePost(id);
			String msg="Deleted Suceesfully";
			return ResponseEntity.ok(msg);
		}
		
	}

}
