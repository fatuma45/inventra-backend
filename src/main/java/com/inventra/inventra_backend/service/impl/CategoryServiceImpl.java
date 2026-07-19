package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.CategoryRequest;
import com.inventra.inventra_backend.dto.response.CategoryResponse;
import com.inventra.inventra_backend.entity.Category;
import com.inventra.inventra_backend.exception.DuplicateResourceException;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.CategoryMapper;
import com.inventra.inventra_backend.repository.CategoryRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               SequenceGeneratorService sequenceGeneratorService) {
        this.categoryRepository = categoryRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new DuplicateResourceException("Category already exists.");
        }

        Category category = new Category();
        category.setCategoryId(sequenceGeneratorService.generateId("CAT"));
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setActive(request.getActive());

        categoryRepository.save(category);

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(String categoryId, CategoryRequest request) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        if (!category.getCategoryName().equals(request.getCategoryName())
                && categoryRepository.existsByCategoryName(request.getCategoryName())) {

            throw new DuplicateResourceException("Category name already exists.");
        }

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setActive(request.getActive());

        categoryRepository.save(category);

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        return CategoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .filter(category -> !category.getDeleted())
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse activateCategory(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        category.setActive(true);

        categoryRepository.save(category);

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse deactivateCategory(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        category.setActive(false);

        categoryRepository.save(category);

        return CategoryMapper.toResponse(category);
    }

    @Override
    public void deleteCategory(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        category.setDeleted(true);
        category.setActive(false);

        categoryRepository.save(category);
    }
}