package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.CategoryRequest;
import com.inventra.inventra_backend.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(String categoryId, CategoryRequest request);

    CategoryResponse getCategoryById(String categoryId);

    List<CategoryResponse> getAllCategories();

    CategoryResponse activateCategory(String categoryId);

    CategoryResponse deactivateCategory(String categoryId);

    void deleteCategory(String categoryId);
}