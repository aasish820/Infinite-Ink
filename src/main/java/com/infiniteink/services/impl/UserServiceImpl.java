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
		userDTO.setId(user.getId());
		userDTO.setFull_name(user.getFull_name());
		userDTO.setAddress(user.getAddress());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setAbout(user.getAbout());
		userDTO.setPosts(user.getPosts().stream()
								.map(this::convertPostToDTO)
								.collect(Collectors.toList()));
		
		return userDTO;
	}
	
	private PostDTO convertPostToDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setTitle(post.getTitle());
		postDTO.setContent(post.getContent());
		return postDTO;
	}
	
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = repo.findAllActiveUser();
		return userList.stream().map(user->convertToDTO(user)).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserByID(Long id) {
		User user = repo.findActiveUserByID(id).orElseThrow(()->new UserNotFoundException("User does not exist"));
		return convertToDTO(user);
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = new User();
		user.setFull_name(userDTO.getFull_name());
		user.setAddress(userDTO.getAddress());
		user.setAbout(userDTO.getAbout());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		User saveduser = repo.save(user);
		return convertToDTO(saveduser);
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User userObj = repo.findActiveUserByID(id).get();
		if(userObj != null) {
			userObj.setFull_name(userDTO.getFull_name());
			userObj.setAddress(userDTO.getAddress());
			userObj.setEmail(userDTO.getEmail());
			userObj.setPassword(userDTO.getPassword());
			userObj.setAbout(userDTO.getAbout());
			User updateUser = repo.save(userObj);
			return convertToDTO(updateUser);
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
