package com.example.library.repositories;

import com.example.library.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    Publisher findPublisherById(Long id);
    boolean existsByName(String name);
}
