package com.example.library.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookLoanRequestDTO {
    private List<Long> bookId;
    private Long userId;
    private Float fines;
    private String returnDate;
}
