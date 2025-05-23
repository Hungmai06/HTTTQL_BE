package com.example.library.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookLoanResponse {
    private Long bookId;
    private Long userId;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private Float deposit;

}
