package com.infiniteink.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.infiniteink.entities.Comment;
import com.infiniteink.entities.Post;
import com.infiniteink.entities.User;
import com.infiniteink.exceptions.CommentNotFoundException;
import com.infiniteink.models.CommentDto;
import com.infiniteink.models.UserDTO;
import com.infiniteink.services.impl.CommentServiceImpl;
import com.infiniteink.services.impl.PostServiceImpl;
import com.infiniteink.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentServiceImpl commentServiceImpl;
	
	@Autowired
	private PostServiceImpl postServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/create")
	public ResponseEntity<?> createComment(@Valid @ModelAttribute("commentDto") CommentDto commentdto) {
		System.out.println(commentdto);
		try {

			Post post = postServiceImpl.getPostbyId(commentdto.getPost_id());
			UserDTO user = userServiceImpl.getUserByID(commentdto.getUser_id());
			Comment comment = commentdto.getComment();
			comment.setPost(post);
			comment.setUser(user.getUser());
			
			Comment savedComment = commentServiceImpl.createComment(comment);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Comment created successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Comment could not be created");
			return ResponseEntity.status(500).body(response);
		}
	}

	@GetMapping("/list")
	public List<Comment> getAllComments() {
		return commentServiceImpl.getAllComments();
	}

	@GetMapping("/{id}/list")
	public ResponseEntity<?> getCommentByID(@PathVariable("id") Long id) {
		try {
			Comment comment = commentServiceImpl.getCommentById(id);
			return ResponseEntity.ok(comment);

		} catch (CommentNotFoundException e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Comment does not exist");
			return ResponseEntity.status(500).body(response);
		}

	}

	@PutMapping("/{id}/update")
	public ResponseEntity<?> updateComment(@PathVariable("id") Long id,
			@Valid @ModelAttribute("comment") Comment comment) {
		try {
			Comment cmt = commentServiceImpl.updateComment(id, comment);
			return ResponseEntity.ok("Comment updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("message", "Comment could not be updated");
			return ResponseEntity.status(500).body(response);
		}
	}

	@PostMapping("/{id}/delete")
	public ResponseEntity<String> deleteComment(@PathVariable Long id) {
		String message = commentServiceImpl.deleteComment(id);
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentServiceImpl.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
