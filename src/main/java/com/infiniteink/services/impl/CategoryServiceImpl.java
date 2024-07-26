package com.infiniteink.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infiniteink.entities.Category;
import com.infiniteink.exceptions.ResourceNotFoundException;
import com.infiniteink.repositories.CategoryRepo;
import com.infiniteink.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.save(category);
	}

	@Override
	public List<Category> viewAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

	@Override
	public Category viewCategoryByID(long id) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
	}

	@Override
	public Category updateCategory(long id, Category categoryDetails) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with Id" + id));
		category.setTitle(categoryDetails.getTitle());
		return categoryRepo.save(category);
	}

	@Override
	public Category deleteCategory(long id) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
		categoryRepo.delete(category);
		return category;
	}

}
