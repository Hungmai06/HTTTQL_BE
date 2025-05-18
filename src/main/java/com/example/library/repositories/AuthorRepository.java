package com.example.library.repositories;

import com.example.library.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    boolean existsByName(String name);
}
