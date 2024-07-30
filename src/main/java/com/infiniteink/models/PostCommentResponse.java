package com.infiniteink.models;

import java.time.LocalDateTime;
import com.infiniteink.entities.Comment;
public class PostCommentResponse {
	
	    private long id;
	    private String comment;
	    private PostUserResponse user; // Assuming PostUserResponse represents user details
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
