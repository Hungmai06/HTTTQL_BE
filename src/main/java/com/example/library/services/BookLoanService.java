package com.example.library.services;

import com.example.library.dtos.request.BookLoanRequestDTO;
import com.example.library.dtos.response.BookLoanResponse;
import com.example.library.dtos.response.ReturnBookResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.BookLoan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookLoanService {

    List<BookLoanResponse> getAllBookLoan(Long userId) throws InvalidValueException;
    BookLoanResponse createBookLoan(BookLoanRequestDTO bookLoanRequestDTO) throws InvalidValueException, DataNotFoundException;
    ReturnBookResponse returnBook(Long bookLoanId) throws DataNotFoundException;

}
