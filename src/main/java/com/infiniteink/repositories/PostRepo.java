package com.infiniteink.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.infiniteink.entities.Post;
@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	@Query("select p from Post p where p.deleted=false")
	List<Post> findAllActivePosts();

	@Query("select p from Post p where p.id=?1 and p.deleted=false")
	Post findActivePostById(Long Id);
	
	@Query("select p from Post p where p.userid=?1 and p.deleted=false")
	List<Post> findActivePostsByUserId(Long userId);

	@Query("select p from Post p where p.userid=?1 and p.deleted=false")
	List<Post> findActivePostsByCategoryId(Long categoryId);
	
	// Soft delete a post by ID
    @Transactional
    @Modifying
    @Query("update Post p set p.deleted_at = CURRENT_TIMESTAMP where p.id = ?1")
    void softDeletePostById(Long id);
 
}
