package com.example.library.services.Impl;

import com.example.library.constants.RoleEnum;
import com.example.library.constants.UserStatusEnum;
import com.example.library.dtos.request.LoginRequest;
import com.example.library.dtos.request.UserRequestDTO;
import com.example.library.dtos.response.LoginResponse;
import com.example.library.dtos.response.UserResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.Role;
import com.example.library.models.User;
import com.example.library.repositories.RoleRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.UserService;
import com.example.library.utils.JwtUtil;
import com.example.library.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    @Override
    public User createUser(UserRequestDTO userRequestDTO) throws InvalidValueException, DataNotFoundException {
       Optional<User> users = userRepository.findByPhone(userRequestDTO.getPhone());
        Role role = roleRepository.findById(userRequestDTO.getRoleId()).orElseThrow(
                ()->  new DataNotFoundException("Can not find role")
        );
        if(!users.isEmpty()){
            throw new InvalidValueException("Phone existed");
        }
            User user = User.builder()
                    .email(userRequestDTO.getEmail())
                    .phone(userRequestDTO.getPhone())
                    .username(userRequestDTO.getUserName())
                    .password(null)
                    .deposit(userRequestDTO.getDeposit())
                    .build();
            user.setStatus(UserStatusEnum.ACTIVE);
            user.setRole(role);
            return userRepository.save(user);

    }

    @Override
    public User updateUser(Long id, UserRequestDTO userRequestDTO) throws DataNotFoundException, InvalidValueException {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("User not found")
        );
        Role role = roleRepository.findById(userRequestDTO.getRoleId()).orElseThrow(
                ()->  new DataNotFoundException("Can not find role")
        );
        User users = User.builder()
                .email(userRequestDTO.getEmail())
                .phone(userRequestDTO.getPhone())
                .username(userRequestDTO.getUserName())
                .password(null)
                .deposit(userRequestDTO.getDeposit())
                .role(role)
                .status(user.getStatus())
                .build();

        return userRepository.save(users);
    }

    @Override
    public User deleteUser(Long id) throws DataNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        user.setStatus(UserStatusEnum.BLOCKED);
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws InvalidValueException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getPhone());
        String token = jwtUtil.generateToken(userDetails);
        return new LoginResponse(token);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) throws DataNotFoundException {
        return userRepository.findById(id).orElseThrow(
                ()-> new  DataNotFoundException("user not found")
        );
    }
}
