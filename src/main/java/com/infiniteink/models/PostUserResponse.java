package com.infiniteink.models;

import java.util.List;
import com.infiniteink.entities.User;
import lombok.Data;
@Data
public class PostUserResponse {
	private long id;
    private String fullName;
    private String address;
    private String email;
    private String password;
    private String about;
    private List<PostResponse> posts;
    private List<PostCommentResponse> comments;
    
    private User user;
}
