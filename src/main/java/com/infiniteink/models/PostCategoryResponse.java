package com.infiniteink.models;

import java.util.List;

import lombok.Data;
import com.infiniteink.entities.Category;
@Data
public class PostCategoryResponse {
	private long id;
    private String title;
    private List<PostResponse> posts;
}