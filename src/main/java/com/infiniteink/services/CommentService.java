package com.infiniteink.services;

import java.util.List;
import com.infiniteink.entities.Comment;

public interface CommentService {

	List<Comment> getAllComments();

	Comment createComment(Comment comment);

	Comment updateComment(Long id, Comment comment);

	String deleteComment(Long id);

	Comment getCommentById(Long id);
	
	List<Comment> getCommentsByPostId(Long postId);
}
