package com.example.todolist.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.TestRequestBodyDto;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String testController(){
        System.out.println("요청 들어오나요");
        return "Hello world";
    }

    @GetMapping("/{id}")
    public String testControllerPathVar(@PathVariable(required = false) int id){
        return "Hello world id " + id;
    }
    
    @GetMapping("/testParam")
    public String testControllerParam(@RequestParam(required = false) int id){
        return "hello world id " + id;
    }
    
    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDto testRequestBodyDto){
        return "Hello World id " + testRequestBodyDto.getId() + " message : " + testRequestBodyDto.getMessage();
    }

    @GetMapping("/testResponseBody")
    public ResponseDto<String> testControllerlResponseDto(){
        List<String> list = new ArrayList<>();
        list.add("hello world responsedto");
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerlResponseEntity(){
        List<String> list = new ArrayList<>();
        list.add("hello world and you got 400");
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }
}
