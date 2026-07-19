package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.CategoryResponse;
import com.inventra.inventra_backend.entity.Category;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryResponse toResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setActive(category.getActive());

        return response;
    }
}
