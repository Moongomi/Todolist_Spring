package com.example.todolist.dto;

import java.time.LocalDate;

import com.example.todolist.Entity.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDto {
    private String id;
    private String title;
    private boolean done;
    private LocalDate duedate;
    private long daysago;

    public TodoDto(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
        this.duedate = entity.getDuedate();
        this.daysago = entity.getDaysago();
    }

    public static TodoEntity toEntity(final TodoDto dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .duedate(dto.getDuedate())
                .daysago(dto.getDaysago())
                .build();
    }
}
