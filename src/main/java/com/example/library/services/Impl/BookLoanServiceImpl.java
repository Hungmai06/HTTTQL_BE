package com.example.library.services.Impl;

import com.example.library.constants.BookLoanEnum;
import com.example.library.constants.BookStatusEnum;
import com.example.library.dtos.request.BookLoanRequestDTO;
import com.example.library.dtos.response.BookLoanResponse;
import com.example.library.dtos.response.ReturnBookResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.Book;
import com.example.library.models.BookLoan;
import com.example.library.models.DateBase;
import com.example.library.models.User;
import com.example.library.repositories.BookLoanRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.BookLoanService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.ParseException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        if(optionalUser.isEmpty()){
            throw new InvalidValueException("User not existed");
        }
        List<BookLoan> bookLoans = bookLoanRepository.findByUserId(userId);
        List<BookLoanResponse> bookLoanResponses = new ArrayList<>();

        for(BookLoan x: bookLoans){
            if(x.getCreatedAt()!=null){
                bookLoanResponses.add(BookLoanResponse.builder()
                        .bookId(x.getBook().getId())
                        .returnDate(x.getReturnDate())
                        .dueDate(x.getDueDate())
                        .borrowDate(x.getBorrowDate())
                        .userId(x.getUser().getId())
                        .build());
            }
            else{
                bookLoanResponses.add(BookLoanResponse.builder()
                        .bookId(x.getBook().getId())
                        .returnDate(null)
                        .dueDate(x.getDueDate())
                        .borrowDate(x.getBorrowDate())
                        .userId(x.getUser().getId())
                        .build());
            }
        }
         return bookLoanResponses;
    }

    @Override
    public BookLoanResponse createBookLoan(BookLoanRequestDTO bookLoanRequestDTO) throws InvalidValueException, DataNotFoundException {

        Optional<Book> optionalBook = bookRepository.findById(bookLoanRequestDTO.getBookId());
        if (optionalBook.isEmpty()) {
            throw new DataNotFoundException("Book not found");
        }
        Optional<User> optionalUser = userRepository.findById(bookLoanRequestDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        Book book = optionalBook.get();
        User user = optionalUser.get();
        user.setDeposit(book.getPriceBook());
        if(book.getBookStatusEnum().equals(BookStatusEnum.UNAVAILABLE)){
            throw new InvalidValueException("Book borrowed");
        }
        System.out.println(bookLoanRequestDTO.getDueDate());
        Date dueDate = bookLoanRequestDTO.getDueDate();

       BookLoan bookLoan = BookLoan.builder()
                .bookLoanEnum(BookLoanEnum.BORROWED)
                .borrowDate(new Date())
                .dueDate( dueDate)
                .book(optionalBook.get())
                .user(optionalUser.get())
                .build();

        book.setBookStatusEnum(BookStatusEnum.UNAVAILABLE);
        bookLoanRepository.save(bookLoan);
        return BookLoanResponse.builder()
                .userId(bookLoanRequestDTO.getUserId())
                .bookId(bookLoanRequestDTO.getBookId())
                .borrowDate(new Date())
                .dueDate(dueDate)
                .build();
    }

    @Override
    public ReturnBookResponse returnBook(Long bookLoanId) throws DataNotFoundException {
        Optional<BookLoan> optionalBookLoan = bookLoanRepository.findById(bookLoanId);
        if(optionalBookLoan.isEmpty()){
            throw new DataNotFoundException("Book Loan not found");
        }
        BookLoan bookLoan = optionalBookLoan.get();
        User user =bookLoan.getUser();
        float fine =0;
        Book book = bookLoan.getBook();
        user.setDeposit(book.getPriceBook());

        Calendar cal = Calendar.getInstance();
        cal.setTime(bookLoan.getDueDate());
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date newdate = cal.getTime();
        if(newdate.compareTo(new Date())<0){
            bookLoan.setBookLoanEnum(BookLoanEnum.NONRETURNABLE);
            book.setBookStatusEnum(BookStatusEnum.LOST);
            fine =user.getDeposit();
            user.setDeposit(0F);
        }
       else{
            bookLoan.setReturnDate(new Date());

            System.out.println(book.getPriceBook());
            if(bookLoan.getDueDate().compareTo(bookLoan.getReturnDate())<=0){
                long millisPerDay = 24 * 60 * 60 * 1000;
                long diff = bookLoan.getReturnDate().getTime() - bookLoan.getDueDate().getTime();
                System.out.println(diff);
                float fines = (float)(diff/millisPerDay)*5;
                if(fines>=user.getDeposit()){
                    fine = user.getDeposit();
                    user.setDeposit(0F);
                }else{
                    fine = fines;
                    user.setDeposit(user.getDeposit()-fines);
                }
                bookLoan.setBookLoanEnum(BookLoanEnum.RETURNED);
                book.setBookStatusEnum(BookStatusEnum.AVAILABLE);
            }
        }
        return ReturnBookResponse.builder()
                .bookStatus(book.getBookStatusEnum())
                .bookLoanEnum(bookLoan.getBookLoanEnum())
                .fines(fine)
                .refund(user.getDeposit())
                .build();
    }


}
