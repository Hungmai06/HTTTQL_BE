package com.example.library.controllers;

import com.example.library.dtos.request.CategoryRequestDTO;
import com.example.library.dtos.response.CategoryResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Category;
import com.example.library.repositories.CategoryRepository;
import com.example.library.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@Valid @PathVariable Long id) throws DataNotFoundException {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryResponse.builder()
                        .name(category.getName())
                        .id(category.getId())
                .build());
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory()  {
        List<CategoryResponse> list = new ArrayList<>();
        for(Category x: categoryService.getAllCategory()){
            list.add(CategoryResponse.builder()
                    .name(x.getName())
                    .id(x.getId())
                    .build());
        }
        return ResponseEntity.ok(list);
    }
    @PostMapping("/")
    public ResponseEntity<?>  createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) throws DataNotFoundException {
        Category category = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok(CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Valid @PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) throws DataNotFoundException {
        Category category = categoryService.updateCategory(id,categoryRequestDTO);
        return ResponseEntity.ok(CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@Valid @PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Category with id= "+id);
    }

}
