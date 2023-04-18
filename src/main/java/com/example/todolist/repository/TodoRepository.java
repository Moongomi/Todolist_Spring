package com.example.todolist.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.todolist.Entity.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String>{
    List<TodoEntity> findByUserId(String userId);

    @Query(value = "SELECT * FROM Todo t where t.user_id = ?1 and t.done = FALSE ORDER BY RAND() LIMIT 1", nativeQuery = true)
    TodoEntity getRandomRow(String userId);

}
