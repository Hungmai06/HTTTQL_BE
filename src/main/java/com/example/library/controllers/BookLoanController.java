package com.example.library.controllers;

import com.example.library.dtos.request.BookLoanRequestDTO;
import com.example.library.dtos.response.ReturnBookResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.services.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book_loan")
@RequiredArgsConstructor
public class BookLoanController {
    private final BookLoanService bookLoanService;
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllBookLoanByUserId(@PathVariable Long userId) throws InvalidValueException {
        return ResponseEntity.ok(bookLoanService.getAllBookLoan(userId));
    }
    @PostMapping("/")
    public ResponseEntity<?> createBookLoan(@RequestBody BookLoanRequestDTO bookLoanRequestDTO) throws InvalidValueException, DataNotFoundException {
        System.out.println(bookLoanRequestDTO.getDueDate());
        return ResponseEntity.ok(bookLoanService.createBookLoan(bookLoanRequestDTO));
    }
    @PutMapping("/{bookLoanId}")
    public ResponseEntity<ReturnBookResponse> returnBookLoan(@PathVariable Long bookLoanId) throws DataNotFoundException {
        return ResponseEntity.ok(bookLoanService.returnBook(bookLoanId));
    }
}
