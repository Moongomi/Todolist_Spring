package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.Entity.UserEntity;
import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.UserDto;
import com.example.todolist.security.Token;
import com.example.todolist.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private Token token;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            if (userDto == null || userDto.getPassword() == null) {
                throw new RuntimeException("Invaild Password");
            }
            UserEntity user = UserEntity.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();

            UserEntity registerUser = userService.create(user);
            UserDto responUserDto = UserDto.builder()
                    .id(registerUser.getId()).username(registerUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responUserDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> auth(@RequestBody UserDto userDto) {
        UserEntity user = userService.getByCredentials(userDto.getUsername(), userDto.getPassword(), passwordEncoder);

        if (user != null) {
            final String useToken = token.create(user);
            final UserDto responUserDto = UserDto.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(useToken)
                    .build();
            return ResponseEntity.ok().body(responUserDto);
        } else {
            ResponseDto responseDto = ResponseDto.builder()
                    .errorMessage("Login error")
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
