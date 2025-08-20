package com.example.todocalendar.todo.dto;

import com.example.todocalendar.todo.entity.TodoEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {

    private String title;
    private String description;
    private Boolean completed;

    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .title(title)
                .description(description)
                .completed(completed)
                .build();
    }
}
