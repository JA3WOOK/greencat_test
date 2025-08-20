package com.example.todocalendar.todo.service;

import com.example.todocalendar.todo.dto.TodoRequest;
import com.example.todocalendar.todo.dto.TodoResponse;
import com.example.todocalendar.todo.entity.TodoEntity;
import com.example.todocalendar.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("새 할일 생성")
    void createTodo() {
        TodoRequest request = new TodoRequest("Test Todo", "This is a test", false);
        TodoEntity savedEntity = TodoEntity.builder().id(1L).title("Test Todo").build();

        when(todoRepository.save(any(TodoEntity.class))).thenReturn(savedEntity);

        TodoResponse result = todoService.createTodo(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Todo");
    }

    @Test
    @DisplayName("모든 할일 조회")
    void getAllTodos() {
        TodoEntity entity = TodoEntity.builder().id(1L).title("Test Todo").build();

        when(todoRepository.findAll()).thenReturn(List.of(entity));

        List<TodoResponse> todos = todoService.getAllTodos();

        assertThat(todos).hasSize(1);
        assertThat(todos.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("ID로 할일 조회")
    void getTodoById() {
        TodoEntity entity = TodoEntity.builder().id(1L).title("Test Todo").build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(entity));

        TodoResponse result = todoService.getTodoById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Todo");
    }

    @Test
    @DisplayName("할일 수정")
    void updateTodo() {
        TodoEntity entity = TodoEntity.builder().id(1L).title("Test Todo").build();
        TodoRequest request = new TodoRequest("Updated Todo", "Updated description", true);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(entity);

        TodoResponse result = todoService.updateTodo(1L, request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Updated Todo");
    }

    @Test
    @DisplayName("할일 삭제")
    void deleteTodo() {
        TodoEntity entity = TodoEntity.builder().id(1L).title("Test Todo").build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(entity));

        todoService.deleteTodo(1L);

        verify(todoRepository, times(1)).delete(entity);
    }
}
