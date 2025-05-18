package com.example.library.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookLoanResponse {
    private Long bookId;
    private Long userId;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;

}
