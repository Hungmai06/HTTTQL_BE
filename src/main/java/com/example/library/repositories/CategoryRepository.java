package com.example.library.repositories;

import com.example.library.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByName(String name);
    Category findCategoryById(Long id);
    boolean existsByName(String name);
}
