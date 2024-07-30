package com.infiniteink.services;

import java.util.List;

import com.infiniteink.entities.Category;

public interface CategoryService {

Category addCategory(Category category);
List<Category> viewAllCategory();
Category viewCategoryByID(long id);
Category updateCategory(long id, Category category);
Category deleteCategory(long id);

}
