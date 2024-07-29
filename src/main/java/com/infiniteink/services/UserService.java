package com.infiniteink.services;

import java.util.List;

import com.infiniteink.entities.User;
import com.infiniteink.models.UserDTO;

public interface UserService {
	
	List<User> getAllUsers();
	
	UserDTO getUserByID(Long id);
	
	User createUser(User user);
	
	User updateUser(Long id, User user);
	
	String deleteUser(Long id);
}
