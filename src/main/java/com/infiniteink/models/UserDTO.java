package com.infiniteink.models;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private long id;
	private String full_name;
	private String address;
	private String email;
	private String password;
	private String about;
	private List<PostDTO> posts;
}
