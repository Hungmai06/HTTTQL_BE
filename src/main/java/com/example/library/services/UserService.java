package com.example.library.services;

import com.example.library.dtos.request.LoginRequest;
import com.example.library.dtos.request.UserRequestDTO;
import com.example.library.dtos.response.LoginResponse;
import com.example.library.dtos.response.UserResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(UserRequestDTO userRequestDTO) throws InvalidValueException, DataNotFoundException;
    User updateUser(Long id, UserRequestDTO userRequestDTO) throws DataNotFoundException, InvalidValueException;
    User deleteUser(Long id) throws DataNotFoundException;
    LoginResponse login(LoginRequest loginRequest) throws InvalidValueException;
    List<User> getAllUser();
    User getUser(Long id) throws DataNotFoundException;
}
