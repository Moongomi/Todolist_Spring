package com.example.todolist.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.Entity.TodoEntity;
import com.example.todolist.dto.ResponseDto;
import com.example.todolist.dto.TodoDto;
import com.example.todolist.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDto<String> responseDto = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto, @AuthenticationPrincipal String userId) {
        try {
            TodoEntity entity = TodoDto.toEntity(dto);
            
            entity.setId(null);
            entity.setUserId(userId);
            
            List<TodoEntity> entities = service.create(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().errorMessage(errorMessage).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllTodoList(@AuthenticationPrincipal String userId) {
        List<TodoEntity> entities = service.findAll(userId);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto, @AuthenticationPrincipal String userId) {
        TodoEntity entity = TodoDto.toEntity(dto);

        entity.setUserId(userId);

        List<TodoEntity> entities = service.update(entity);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> delTodo(@RequestBody TodoDto dto, @AuthenticationPrincipal String userId) {
        try {
            TodoEntity entity = TodoDto.toEntity(dto);

            entity.setUserId(userId);

            List<TodoEntity> entities = service.delete(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().errorMessage(errorMessage).build();
            return ResponseEntity.badRequest().body(response);
        }

    }
}
