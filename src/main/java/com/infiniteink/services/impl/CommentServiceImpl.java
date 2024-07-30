package com.infiniteink.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infiniteink.entities.Comment;
import com.infiniteink.exceptions.CommentNotFoundException;
import com.infiniteink.repositories.CommentRepo;
import com.infiniteink.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Override
	public List<Comment> getAllComments() {
		// TODO Auto-generated method stub
		return commentRepo.findAllActiveComment();
	}

	@Override
	public Comment createComment(Comment comment) {
		// TODO Auto-generated method stub
		Comment cmt = new Comment();
		cmt.setComment(comment.getComment());
		cmt.setPost(comment.getPost());
		cmt.setUser(comment.getUser());
		Comment saveComment = commentRepo.save(cmt);
		
		return saveComment;
	}

	@Override
	public Comment updateComment(Long id, Comment comment) {
		// TODO Auto-generated method stub
		// check if records exists or not
		Comment cmt = commentRepo.findActiveCommentByID(id).get();
		if (cmt != null) {
			//cmt.setComment(comment.getComment());
			System.out.println(comment.getComment());
			Comment updateComment = commentRepo.save(cmt);
			return updateComment;

		}
		return null;
	}

	@Override
	public String deleteComment(Long id) {
		// TODO Auto-generated method stub
		String message = "Comment delete successfully";
		Comment comment = commentRepo.findActiveCommentByID(id).get();
		if (comment != null) {
			commentRepo.deleteCommentById(id);
		} else {
			throw new CommentNotFoundException("Comment does not exist");
		}
		return message;
	}

	@Override
	public Comment getCommentById(Long id) {
		// TODO Auto-generated method stub
		Comment comment = commentRepo.findActiveCommentByID(id)
				.orElseThrow(() -> new CommentNotFoundException("Comment does not exist"));
		return comment;
	}

	@Override
	public List<Comment> getCommentsByPostId(Long postId) {
		// TODO Auto-generated method stub
		return commentRepo.findByPost_Id(postId);
	}

}
