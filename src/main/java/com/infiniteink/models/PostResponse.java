package com.infiniteink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infiniteink.entities.Post;

import lombok.Data;

@Data
public class PostResponse {
//	private Long id;
	private String title;
	private String content;
//	private List<PostUserResponse> users;
//	private List<PostCategoryResponse> category;
	
	@JsonIgnore
	private Long user_id;
	
	@JsonIgnore
	private Long category_id;

}
