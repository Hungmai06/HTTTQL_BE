package com.example.library.controllers;

import com.example.library.dtos.request.LoginRequest;
import com.example.library.dtos.request.UserRequestDTO;
import com.example.library.dtos.response.LoginResponse;
import com.example.library.dtos.response.UserResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.User;
import com.example.library.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody  LoginRequest loginRequest) throws InvalidValueException {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        List<UserResponse> listUser = new ArrayList<>();
        for(User x: userService.getAllUser()){
            UserResponse userResponse = UserResponse
                    .builder()
                    .id(x.getId())
                    .phone(x.getPhone())
                    .email(x.getEmail())
                    .userName(x.getUsername())
                    .deposit(x.getDeposit())
                    .status(x.getStatus())
                    .build();
            listUser.add(userResponse);
        }

        return ResponseEntity.ok(listUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@Valid @PathVariable Long id) throws DataNotFoundException {
        User user = userService.getUser(id);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .status(user.getStatus())
                .userName(user.getUsername())
                .email(user.getEmail())
                .deposit(user.getDeposit())
                .build();
        return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws InvalidValueException, DataNotFoundException {
           User user = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(UserResponse.builder()
                        .phone(user.getPhone())
                        .id(user.getId())
                        .email(user.getEmail())
                        .userName(user.getUsername())
                        .deposit(user.getDeposit())
                        .status(user.getStatus())
                .build());
    }
    @PutMapping("/{id}")
    public UserResponse updateUser(@Valid @PathVariable Long id,@Valid @RequestBody UserRequestDTO userRequestDTO) throws InvalidValueException, DataNotFoundException {
     User user = userService.updateUser(id,userRequestDTO);
     return UserResponse.builder()
             .id(user.getId())
             .phone(user.getPhone())
             .status(user.getStatus())
             .userName(user.getUsername())
             .deposit(user.getDeposit())
             .email(user.getEmail())
             .build();
    }
    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@Valid @PathVariable Long id) throws DataNotFoundException {
        User user = userService.deleteUser(id);
        return UserResponse.builder()
                .id(user.getId())
                .status(user.getStatus())
                .phone(user.getPhone())
                .userName(user.getUsername())
                .email(user.getEmail())
                .deposit(user.getDeposit())
                .build();
    }
}
