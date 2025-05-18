package com.example.library.dtos.request;

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
public class BookRequestDTO {
    private String name;
    private Float priceBook;
    private String description;
    private Long categoryId;
    private Long authorId;
    private Long publisherId;
}
