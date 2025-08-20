package com.example.todocalendar.todo.controller;

import com.example.todocalendar.todo.dto.TodoRequest;
import com.example.todocalendar.todo.dto.TodoResponse;
import com.example.todocalendar.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Tag(name = "할일 이벤트", description = "할일 이벤트 관리 API")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "새 할일 생성", description = "제공된 세부 정보로 새 할일을 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할일이 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.createTodo(todoRequest));
    }

    @GetMapping
    @Operation(summary = "모든 할일 조회", description = "모든 할일 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "할일 목록을 성공적으로 조회함")
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID로 할일 조회", description = "특정 ID로 할일을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할일을 성공적으로 조회함"),
            @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없음")
    })
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "할일 수정", description = "제공된 세부 정보로 기존 할일을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "할일이 성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터"),
            @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없음")
    })
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "할일 삭제", description = "ID로 할일을 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "할일이성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
