package com.example.library.dtos.response;

import com.example.library.constants.BookLoanEnum;
import com.example.library.constants.BookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookResponse {
    private BookStatusEnum bookStatus;
    private BookLoanEnum bookLoanEnum;
    private float fines;
    private float refund;

}
