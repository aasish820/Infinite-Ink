package com.infiniteink.services;

import java.util.List;

import com.infiniteink.models.UserDTO;

public interface UserService {
	
	List<UserDTO> getAllUsers();
	
	UserDTO getUserByID(Long id);
	
	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(Long id, UserDTO userDTO);
	
	String deleteUser(Long id);
}
