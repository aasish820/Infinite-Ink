package com.infiniteink.models;

import com.infiniteink.entities.Comment;

import lombok.Data;

@Data
public class CommentDto {
	private Comment comment;
	private Long post_id;
	private Long user_id;
}
