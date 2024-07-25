package com.infiniteink.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infiniteink.entities.User;
import com.infiniteink.exceptions.UserNotFoundException;
import com.infiniteink.repositories.UserRepo;
import com.infiniteink.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repo;
		
	@Override
	public List<User> getAllUsers() {
		return repo.findAllActiveUser();
	}

	@Override
	public User getUserByID(Long id) {
		User user = repo.findActiveUserByID(id).orElseThrow(()->new UserNotFoundException("User does not exist"));
		return user;
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
