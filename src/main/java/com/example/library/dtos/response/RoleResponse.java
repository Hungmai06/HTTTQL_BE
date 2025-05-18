package com.example.library.dtos.response;


import com.example.library.constants.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class RoleResponse {
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}