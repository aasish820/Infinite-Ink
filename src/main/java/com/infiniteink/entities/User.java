package com.infiniteink.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Name cannot be empty")
	private String full_name;
	
	@NotBlank(message = "Address cannot be empty")
	private String address;
	
	@Email(message = "Invalid email address")
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	
	@NotBlank(message = "Password cannot be emppty")
	private String password;
	private String about;
	
	@JsonIgnore
	private LocalDateTime deleted_at;
	
	@CreationTimestamp
	@JsonIgnore
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	@JsonIgnore
	private LocalDateTime updated_at;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Post> posts = new ArrayList<>(); 
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Comment> comments = new ArrayList<>();
}
