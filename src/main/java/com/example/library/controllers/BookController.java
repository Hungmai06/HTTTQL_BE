package com.example.library.controllers;

import com.example.library.constants.BookStatusEnum;
import com.example.library.dtos.request.BookRequestDTO;
import com.example.library.dtos.response.BookResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.Book;
import com.example.library.services.BookService;
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
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@Valid @PathVariable Long id) throws DataNotFoundException {
        Book book = bookService.getBook(id);

        return ResponseEntity.ok(BookResponse.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .description(book.getDescription())
                        .priceBook(book.getPriceBook())
                        .categoryId(book.getCategory().getName())
                        .publisherId(book.getPublisher().getName())
                        .authorId(book.getAuthor().getName())
                        .status(book.getBookStatusEnum())
                .build());
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllBook(){
        List<BookResponse> list = new ArrayList<>();
        for(Book book: bookService.getAllBook()){
            list.add(BookResponse.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .description(book.getDescription())
                            .priceBook(book.getPriceBook())
                            .categoryId(book.getCategory().getName())
                            .publisherId(book.getPublisher().getName())
                            .authorId(book.getAuthor().getName())
                            .status(book.getBookStatusEnum())
                    .build());
        }
        return  ResponseEntity.ok(list);
    }
    @PostMapping("/")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) throws InvalidValueException {
        Book book = bookService.createBook(bookRequestDTO);
        return ResponseEntity.ok(BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .priceBook(book.getPriceBook())
                .categoryId(book.getCategory().getName())
                .publisherId(book.getPublisher().getName())
                .authorId(book.getAuthor().getName())
                .status(book.getBookStatusEnum())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@Valid @PathVariable Long id, @Valid @RequestBody BookRequestDTO bookRequestDTO) throws InvalidValueException, DataNotFoundException {
        Book book = bookService.updateBook(id,bookRequestDTO);
        return ResponseEntity.ok(BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .priceBook(book.getPriceBook())
                .categoryId(book.getCategory().getName())
                .publisherId(book.getPublisher().getName())
                .authorId(book.getAuthor().getName())
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@Valid @PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("delete book with id = "+id);
    }
}
