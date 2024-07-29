package com.infiniteink.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.infiniteink.entities.Comment;
import jakarta.transaction.Transactional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

	@Query("SELECT c from Comment c WHERE c.deleted_at IS NULL")
	List<Comment> findAllActiveComment();

	@Query("SELECT c from Comment c WHERE c.id = :id and c.deleted_at IS NULL")
	Optional<Comment> findActiveCommentByID(Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Comment c SET c.deleted_at = CURRENT_TIMESTAMP WHERE c.id = :id AND c.deleted_at IS NULL", nativeQuery = true)
	void deleteCommentById(@Param("id") Long id);
	
	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.deleted_at IS NULL")
    List<Comment> findByPost_Id(@Param("postId") Long postId);
}
