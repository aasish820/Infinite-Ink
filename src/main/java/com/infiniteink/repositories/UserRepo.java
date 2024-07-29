package com.infiniteink.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infiniteink.entities.User;
import com.infiniteink.models.UserDTO;

import jakarta.transaction.Transactional;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("SELECT u from User u WHERE u.deleted_at IS NULL")
	List<User> findAllActiveUser();
	
	@Query("SELECT u from User u WHERE u.id = :id and u.deleted_at IS NULL")
	Optional<User> findActiveUserByID(Long id);
	
	@Modifying
	@Transactional
    @Query(value = "UPDATE User u SET u.deleted_at = CURRENT_TIMESTAMP WHERE u.id = :id AND u.deleted_at IS NULL", nativeQuery = true)
	void deleteUserById(@Param("id") Long id);
}
