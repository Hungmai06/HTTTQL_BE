package com.example.library.repositories;

import com.example.library.dtos.request.BookLoanRequestDTO;
import com.example.library.models.BookLoan;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookLoanRepository extends JpaRepository<BookLoan,Long> {
    List<BookLoan> findByUserId(Long userId);
    Optional<BookLoan> findByBookId(Long bookId);
}
