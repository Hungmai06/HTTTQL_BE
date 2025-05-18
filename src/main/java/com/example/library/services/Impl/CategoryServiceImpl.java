package com.example.library.services.Impl;

import com.example.library.dtos.request.CategoryRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Category;
import com.example.library.repositories.CategoryRepository;
import com.example.library.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) throws DataNotFoundException {
        return categoryRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Cannot find Category with id= "+id)
        );
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRequestDTO categoryRequestDTO) throws DataNotFoundException {
        if(categoryRepository.existsByName(categoryRequestDTO.getName())){
            throw new DataNotFoundException("can not register because your name existed ");
        }

        categoryRepository.save(Category.builder()
                        .name(categoryRequestDTO.getName())
                .build());
        Category category = categoryRepository.findCategoryByName(categoryRequestDTO.getName());
        return Category.builder()
                .name(category.getName())
                .id(category.getId())
                .build();
    }

    @Override
    public Category updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) throws DataNotFoundException {
        Category category = categoryRepository.findCategoryById(id);
        if(category == null){
            throw new DataNotFoundException("Cannot find Category with id= "+id);
        }
        category.setName(categoryRequestDTO.getName());
        categoryRepository.save(category);
        return  category;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
