package com.infiniteink.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.infiniteink.entities.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	@Query("select p from Post p where p.deleted_at IS NULL")
	List<Post> findAllActivePosts();

	@Query("select p from Post p where p.id=?1 and p.deleted_at IS NULL")
	Optional<Post> findActivePostById(Long Id);
	
	@Query("select p from Post p where p.user.id=?1 and p.deleted_at IS NULL")
	List<Post> findActivePostsByUserId(Long user_id);

	@Query("select p from Post p where p.category.id=?1 and p.deleted_at IS NULL")
	List<Post> findActivePostsByCategoryId(Long category_id);
	
	// Soft delete a post by ID
    @Transactional
    @Modifying
    @Query("update Post p set p.deleted_at = CURRENT_TIMESTAMP where p.id = ?1")
    void softDeletePostById(Long id);

	
	
}
