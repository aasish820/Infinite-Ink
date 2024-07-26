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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.infiniteink.entities.Comment;
import com.infiniteink.exceptions.CommentNotFoundException;
import com.infiniteink.services.impl.CommentServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentServiceImpl commentServiceImpl;

	@PostMapping("/create")
	public ResponseEntity<?> createComment(@Valid @ModelAttribute("Comment") Comment comment) {
		try {
			Comment cmt = new Comment();
			System.out.println(comment.getComment());
			
			cmt.setComment(comment.getComment());

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
}
