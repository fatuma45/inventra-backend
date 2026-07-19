package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.CategoryRequest;
import com.inventra.inventra_backend.dto.response.CategoryResponse;
import com.inventra.inventra_backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategory(
            @PathVariable String categoryId,
            @Valid @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(categoryId, request);
    }

    @PatchMapping("/{categoryId}/activate")
    public CategoryResponse activateCategory(@PathVariable String categoryId) {
        return categoryService.activateCategory(categoryId);
    }

    @PatchMapping("/{categoryId}/deactivate")
    public CategoryResponse deactivateCategory(@PathVariable String categoryId) {
        return categoryService.deactivateCategory(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}