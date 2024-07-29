package com.infiniteink.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infiniteink.entities.Comment;
import com.infiniteink.entities.Post;
import com.infiniteink.entities.User;
import com.infiniteink.exceptions.UserNotFoundException;
import com.infiniteink.models.CommentDto;
import com.infiniteink.models.PostDTO;
import com.infiniteink.models.UserDTO;
import com.infiniteink.repositories.UserRepo;
import com.infiniteink.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repo;
	
	
	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUser(user);
		userDTO.setPosts(user.getPosts().stream()
								.map(this::convertPostToDTO)
								.collect(Collectors.toList()));
		userDTO.setComments(user.getComments().stream()
								.map(this::convertCommentToDTO)
								.collect(Collectors.toList()));
		
		return userDTO;
	}
	
	private PostDTO convertPostToDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setPost(post);
		return postDTO;
	}
	
	private CommentDto convertCommentToDTO(Comment comment) {
		CommentDto commentDTO = new CommentDto();
		commentDTO.setComment(comment);
		return commentDTO;
	}
	
	
	@Override
	public List<User> getAllUsers() {
		List<User> userList = repo.findAllActiveUser();
		userList.stream().forEach(item->convertToDTO(item));
		return userList;
	}

	@Override
	public UserDTO getUserByID(Long id) {
		User user = repo.findActiveUserByID(id).orElseThrow(()->new UserNotFoundException("User does not exist"));
		return convertToDTO(user);
	}

	@Override
	public User createUser(User user) {
		User saveUser = repo.save(user);
		return saveUser;
	}

	@Override
	public User updateUser(Long id, User user) {
		User userObj = repo.findActiveUserByID(id).get();
		if(userObj != null) {
			userObj.setFull_name(user.getFull_name());
			userObj.setAddress(user.getAddress());
			userObj.setEmail(user.getEmail());
			userObj.setPassword(user.getPassword());
			userObj.setAbout(user.getAbout());
			User updateUser = repo.save(userObj);
			return updateUser;
		}
		return null;
	}

	@Override
	public String deleteUser(Long id) {
		String msg = "User deleted successfully";
		User userObj = repo.findActiveUserByID(id).get();
		if(userObj != null) {
			repo.deleteUserById(id);
		} else {
			throw new UserNotFoundException("User does not exist");
		}
		return msg;
	}

}
