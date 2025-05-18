package com.example.library.services;

import com.example.library.dtos.request.BookRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BookService {
    Book getBook(Long id) throws DataNotFoundException;
    List<Book> getAllBook();
    Book createBook(BookRequestDTO bookRequestDTO) throws InvalidValueException;
    Book updateBook(Long id, BookRequestDTO bookRequestDTO) throws DataNotFoundException, InvalidValueException;
    void deleteBook(Long id);
}
