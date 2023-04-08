package com.example.todolist.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.todolist.Entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

@Slf4j
@Service
public class Token {
    private static final String SECRET_KEY = "ItisSecretKey";

    public String create(UserEntity userEntity){
        Date expireDate = Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
        return Jwts.builder()
        .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
        .setSubject(userEntity.getId())
        .setIssuer("TodoApp")
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .compact();
    }

    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
        return claims.getSubject();
    }
}
