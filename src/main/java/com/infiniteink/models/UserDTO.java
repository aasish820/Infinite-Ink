package com.infiniteink.models;

import java.util.List;

import com.infiniteink.entities.User;

import lombok.Data;

@Data
public class UserDTO {

	private User user;
	private List<PostDTO> posts;
	private List<CommentDto> comments;
}
