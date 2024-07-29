package com.infiniteink.models;

import java.util.List;

import com.infiniteink.entities.Category;

import lombok.Data;

@Data
public class CategoryDTO {
	private Category category;
	private List<PostDTO> posts;
}
