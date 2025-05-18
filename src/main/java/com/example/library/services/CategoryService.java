package com.example.library.services;

import com.example.library.dtos.request.CategoryRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category getCategoryById(Long id) throws DataNotFoundException;
    List<Category> getAllCategory();
    Category createCategory(CategoryRequestDTO categoryRequestDTO) throws DataNotFoundException;
    Category updateCategory(Long id,CategoryRequestDTO categoryRequestDTO) throws DataNotFoundException;
    void deleteCategory(Long id);
}
