package com.example.library.dtos.request;

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
public class RoleRequestDTO {
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
