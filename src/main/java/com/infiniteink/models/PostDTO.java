package com.infiniteink.models;

import java.util.List;

import com.infiniteink.entities.Post;

import lombok.Data;

@Data
public class PostDTO {
	private long id;
	private String title;
	private String content;
}
