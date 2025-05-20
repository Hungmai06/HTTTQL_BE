package com.example.library.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private Float priceBook;
    private String description;
    private String categoryId;
    private String authorId;
    private String publisherId;
    private Enum status;
}
