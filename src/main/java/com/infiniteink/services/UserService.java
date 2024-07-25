package com.infiniteink.services;

import java.util.List;

import com.infiniteink.entities.User;

public interface UserService {
	List<User> getAllUsers();
	
	User getUserByID(Long id);
	
	User createUser(User user);
	
	User updateUser(Long id, User user);
	
	String deleteUser(Long id);
}
