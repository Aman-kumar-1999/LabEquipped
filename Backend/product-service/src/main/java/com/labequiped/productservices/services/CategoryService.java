package com.labequiped.productservices.services;

import java.util.List;

import com.labequiped.productservices.entities.Category;


public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category createCategory(Category category);

    Category updateCategory(String id, Category category);

    void deleteCategory(String id);

}
