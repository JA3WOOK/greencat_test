package com.example.todocalendar.todo.controller;

import com.example.todocalendar.todo.dto.TodoRequest;
import com.example.todocalendar.todo.dto.TodoResponse;
import com.example.todocalendar.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/todos - 새 할일 생성")
    void createTodo() throws Exception {
        TodoRequest request = new TodoRequest(); // 필요한 필드 채워도 되고
        TodoResponse response = TodoResponse.builder().id(1L).build();

        Mockito.when(todoService.createTodo(any(TodoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/todos - 모든 할일 조회")
    void getAllTodos() throws Exception {
        TodoResponse response = TodoResponse.builder().id(1L).build();

        Mockito.when(todoService.getAllTodos()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/todos/{id} - 특정 할일 조회")
    void getTodoById() throws Exception {
        TodoResponse response = TodoResponse.builder().id(1L).build();

        Mockito.when(todoService.getTodoById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/todos/{id} - 할일 수정")
    void updateTodo() throws Exception {
        TodoRequest request = new TodoRequest();
        TodoResponse response = TodoResponse.builder().id(1L).build();

        Mockito.when(todoService.updateTodo(eq(1L), any(TodoRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/todos/{id} - 할일 삭제")
    void deleteTodo() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }
}
