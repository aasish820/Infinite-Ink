package com.infiniteink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infiniteink.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>{

}
