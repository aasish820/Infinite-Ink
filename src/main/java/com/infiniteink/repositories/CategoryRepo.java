package com.infiniteink.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.infiniteink.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.deleted_at IS NULL")
    List<Category> viewAllCategory();

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.deleted_at IS NULL")
    Optional<Category> findById(Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.deleted_at = CURRENT_TIMESTAMP WHERE c.id = :id AND c.deleted_at IS NULL")
    void softDeleteCategoryById(@Param("id") Long id);
}
