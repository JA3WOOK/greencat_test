package com.example.todocalendar.todo.dto;

import com.example.todocalendar.todo.entity.TodoEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;

    public static TodoResponse toDto(TodoEntity entity) {
        return TodoResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .completed(entity.getCompleted())
                .build();
    }
}
