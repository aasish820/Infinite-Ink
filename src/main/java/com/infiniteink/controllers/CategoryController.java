package com.infiniteink.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infiniteink.entities.Category;
import com.infiniteink.exceptions.ResourceNotFoundException;
import com.infiniteink.services.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @ModelAttribute("category") Category category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category could not be added");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/list")
    public List<Category> getAllCategories() {
        return categoryService.viewAllCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.viewCategoryByID(id);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category does not exist");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return ResponseEntity.ok("Category updated successfully");
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category could not be updated");
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category could not be updated");
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            Category deletedCategory = categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Category not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Category could not be deleted");
        }
    }
}
