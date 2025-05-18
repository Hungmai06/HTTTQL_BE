package com.example.library.dtos.response;

import com.example.library.constants.UserStatusEnum;
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
public class UserResponse {
    private String phone;
    private Long id;
    private String userName;
    private String email;
    private Float deposit;
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;
}
