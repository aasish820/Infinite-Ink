package com.infiniteink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infiniteink.entities.Comment;

import lombok.Data;

@Data
public class CommentDto {
	private String comment;
	
	@JsonIgnore
	private Long post_id;
	
	@JsonIgnore
	private Long user_id;
}
