package com.example.todolist.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todolist.Entity.TodoEntity;
import com.example.todolist.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        TodoEntity entity = TodoEntity.builder().title("Yeah This is My first title").build();
        repository.save(entity);
        TodoEntity findEntity = repository.findById(entity.getId()).get();
        return findEntity.getTitle();
    }

    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity is null");
            throw new RuntimeException("Entity is null");
        }

        if (entity.getUserId() == null) {
            log.warn("Userid wrong.");
            throw new RuntimeException("Userid wrong.");
        }
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);

        LocalDate currentDate = LocalDate.now();
        entity.setDuedate(currentDate);

        long daysAgo = ChronoUnit.DAYS.between(entity.getDuedate(), currentDate);
        entity.setDaysago(daysAgo);
        entity.setSpendtime(0);

        repository.save(entity);

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> findAll(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        final Optional<TodoEntity> source = repository.findById(entity.getId());

        source.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todo.setSpendtime(entity.getSpendtime());
            repository.save(todo);
        });

        return findAll(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("delete error : ", entity.getId(), e);
            throw new RuntimeException("delete error : " + entity.getId());
        }

        return findAll(entity.getUserId());
    }

    public TodoEntity random(final String userId){
        try{
            return repository.getRandomRow(userId);
        } catch (Exception e){
            throw new RuntimeException("random error");
        }
        
    }
}
