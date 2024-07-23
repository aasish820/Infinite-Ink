package com.infiniteink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infiniteink.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
