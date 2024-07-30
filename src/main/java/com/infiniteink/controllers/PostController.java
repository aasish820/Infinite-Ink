package com.infiniteink.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infiniteink.entities.Post;
import com.infiniteink.exceptions.PostNotFoundException;
import com.infiniteink.services.impl.PostServiceImpl;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostServiceImpl postService;
	
	@PostMapping("/create")
    public ResponseEntity<Post> createPost(@ModelAttribute("Post") Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
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
    
   private static final String UPLOAD_DIR="uploads/";
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Map<String,String>>uploadImage(@PathVariable Long id,@RequestParam("image") MultipartFile imageFile )
    {
    	Post postI=postService.getPostbyId(id);
    	
    	Path filePath=Paths.get(UPLOAD_DIR+imageFile.getOriginalFilename());
    	try {
			Files.createDirectories(filePath.getParent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			Files.write(filePath,imageFile.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String imageUrl="/uploads/"+imageFile.getOriginalFilename();
    	postI.setImage(imageUrl);
    	Map<String,String>response=new HashMap<>();
    	response.put("message", "image upload sucessfull");
    	response.put("message", imageUrl);
    	return ResponseEntity.ok(response);
    }
    }

	


