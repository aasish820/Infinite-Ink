package com.infiniteink.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infiniteink.entities.User;
import com.infiniteink.exceptions.UserNotFoundException;
import com.infiniteink.models.UserDTO;
import com.infiniteink.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl impl;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @ModelAttribute("userdto") UserDTO userDTO) {
		try {			
			UserDTO savedUser = impl.createUser(userDTO);
			Map<String, String> response = new HashMap<>();
			response.put("message", "User registered successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,String>response=new HashMap<>();
			response.put("message", "User could not be registered");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = impl.getAllUsers();
		return ResponseEntity.ok(userDTOs);
	}
	
	@GetMapping("/{id}/list")
	public ResponseEntity<?> getUserByID(@PathVariable("id") Long id) {
		try {
			UserDTO userDTO = impl.getUserByID(id);
	        return ResponseEntity.ok(userDTO);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			Map<String,String>response=new HashMap<>();
			response.put("message", "User does not exist");
			return ResponseEntity.status(500).body(response);
		}
		
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute("userdto") UserDTO userDTO) {
		try {
			UserDTO userobj = impl.updateUser(id, userDTO);
			return ResponseEntity.ok("User updated successfully");
		} catch(Exception e) {
			e.printStackTrace();
			Map<String,String>response=new HashMap<>();
			response.put("message", "User could not be updated");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping("/{id}/delete")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		String msg = impl.deleteUser(id);
		return ResponseEntity.ok(msg);
	}
}
