package com.example.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todolist.Entity.UserEntity;
import com.example.todolist.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public UserEntity create(final UserEntity entity){
        if (entity == null || entity.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String username = entity.getUsername();
        if(userRepo.existsByUsername(username)){
            log.warn("Username exists {}",username);
            throw new RuntimeException("Username exists");
        }
        return userRepo.save(entity);
    }

    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
        final UserEntity orguser = userRepo.findByUsername(username);
        log.info("입력된 비밀번호 : "+password+" 원래 비밀번호 : "+orguser.getPassword());
        if(orguser!=null && encoder.matches(password, orguser.getPassword())){
            return orguser;
        }
        return null;
    }
}
