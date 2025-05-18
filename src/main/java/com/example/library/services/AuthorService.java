package com.example.library.services;

import com.example.library.dtos.request.AuthorRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    Author getAuthor(Long id) throws DataNotFoundException;
    List<Author> getAllAuthor();
    Author createAuthor(AuthorRequestDTO authorRequestDTO) throws DataNotFoundException;
    Author updateAuthor(Long id, AuthorRequestDTO authorRequestDTO) throws DataNotFoundException;
    void deleteAuthor(Long id);
}
