package com.example.library.services.Impl;

import com.example.library.constants.BookLoanEnum;
import com.example.library.constants.BookStatusEnum;
import com.example.library.dtos.request.BookLoanRequestDTO;
import com.example.library.dtos.request.ReturnBookLoanRequestDTO;
import com.example.library.dtos.response.BookLoanResponse;
import com.example.library.dtos.response.ReturnBookResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.Book;
import com.example.library.models.BookLoan;
import com.example.library.models.User;
import com.example.library.repositories.BookLoanRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookLoanServiceImpl implements BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public List<BookLoanResponse> getAllBookLoan(Long userId) throws InvalidValueException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new InvalidValueException("User not existed");
        }

        List<BookLoan> bookLoans = bookLoanRepository.findByUserId(userId);
        List<BookLoanResponse> bookLoanResponses = new ArrayList<>();

        for (BookLoan loan : bookLoans) {
            Long bookId = loan.getBook() != null ? loan.getBook().getId() : null;

            bookLoanResponses.add(BookLoanResponse.builder()
                    .bookId(bookId)
                    .returnDate(loan.getReturnDate())
                    .dueDate(loan.getDueDate())
                    .borrowDate(loan.getBorrowDate())
                    .userId(userId)
                    .build());
        }

        return bookLoanResponses;
    }


    @Override
    public List<BookLoanResponse> createBookLoan(BookLoanRequestDTO bookLoanRequestDTO) throws InvalidValueException, DataNotFoundException {

        Optional<User> optionalUser = userRepository.findById(bookLoanRequestDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("User not found");

        }
        User user = optionalUser.get();

        List<BookLoanResponse> responses = new ArrayList<>();

        for (Long bookId : bookLoanRequestDTO.getBookId()) {
            Optional<Book> optionalBook = bookRepository.findById(bookId);
            if (optionalBook.isEmpty()) {
               throw new DataNotFoundException("Book not found with ID: " + bookId);
            }

            Book book = optionalBook.get();

            if (book.getBookStatusEnum().equals(BookStatusEnum.UNAVAILABLE)) {
                continue;
                //throw new InvalidValueException("Book already borrowed: ID = " + bookId);
            }

            user.setDeposit(book.getPriceBook()); // Nếu mỗi sách cộng thêm tiền đặt cọc, hoặc xử lý khác nếu chỉ đặt 1 lần

            BookLoan bookLoan = BookLoan.builder()
                    .bookLoanEnum(BookLoanEnum.BORROWED)
                    .borrowDate(bookLoanRequestDTO.getBorrowDate())
                    .dueDate(bookLoanRequestDTO.getDueDate())
                    .book(book)
                    .user(user)
                    .build();

            book.setBookStatusEnum(BookStatusEnum.UNAVAILABLE);
            bookLoanRepository.save(bookLoan);
            System.out.println(bookLoanRequestDTO.getDueDate());
            BookLoanResponse response = BookLoanResponse.builder()
                    .userId(user.getId())
                    .bookId(bookId)
                    .borrowDate(bookLoan.getBorrowDate())
                    .dueDate(bookLoan.getDueDate())
                    .deposit(user.getDeposit())
                    .build();

            responses.add(response);
        }

        return responses;
    }
    @Override
    public Boolean returnBook(ReturnBookLoanRequestDTO returnBookLoanRequestDTO) throws DataNotFoundException {

        List<BookLoan> bookLoans = bookLoanRepository.findByUserId(returnBookLoanRequestDTO.getUserId());

        List<Long> returnBookIds = returnBookLoanRequestDTO.getBookId();
        for (BookLoan bookLoan : bookLoans) {
            Book book = bookLoan.getBook();

            // Nếu sách thuộc danh sách cần trả thì xử lý
            if (returnBookIds.contains(book.getId())) {
                // Cập nhật trạng thái sách
                book.setBookStatusEnum(BookStatusEnum.AVAILABLE);
                bookRepository.save(book);

                // Xoá BookLoan
                bookLoanRepository.delete(bookLoan);
            }

            }

        return true;
    }
}
