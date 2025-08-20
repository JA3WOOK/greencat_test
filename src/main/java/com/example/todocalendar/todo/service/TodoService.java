package com.example.todocalendar.todo.service;

import com.example.todocalendar.todo.dto.TodoRequest;
import com.example.todocalendar.todo.dto.TodoResponse;
import com.example.todocalendar.todo.entity.TodoEntity;
import com.example.todocalendar.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(TodoResponse::toDto)
                .toList();
    }

    public TodoResponse getTodoById(Long id) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id " + id));
        return TodoResponse.toDto(entity);
    }

    public TodoResponse createTodo(TodoRequest todoRequest) {
        TodoEntity todoEntity = todoRepository.save(todoRequest.toEntity());
        return TodoResponse.toDto(todoEntity);
    }

    public TodoResponse updateTodo(Long id, TodoRequest todoRequest) {
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id " + id));

        if (todoRequest.getTitle() != null) todoEntity.setTitle(todoRequest.getTitle());
        if (todoRequest.getDescription() != null) todoEntity.setDescription(todoRequest.getDescription());
        if (todoRequest.getCompleted() != null) todoEntity.setCompleted(todoRequest.getCompleted());

        todoRepository.save(todoEntity);
        return TodoResponse.toDto(todoEntity);
    }

    public void deleteTodo(Long id) {
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id " + id));
        todoRepository.delete(todoEntity);
    }
}
