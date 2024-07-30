package com.infiniteink.models;

import java.util.List;

import lombok.Data;
@Data
public class PostResponse {
	private Long id;
	private String title;
	private String content;
	private List<PostUserResponse> users;
	private List<PostCategoryResponse> category;

}
