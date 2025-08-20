package com.example.todocalendar.calendar.controller;

import com.example.todocalendar.calendar.dto.CalendarEventRequest;
import com.example.todocalendar.calendar.dto.CalendarEventResponse;
import com.example.todocalendar.calendar.service.CalendarEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Tag(name = "일정 이벤트", description = "일정 이벤트 관리 API")
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    @PostMapping
    @Operation(summary = "새 일정 이벤트 생성", description = "제공된 세부 정보로 새 일정 이벤트를 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트가 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    public ResponseEntity<CalendarEventResponse> createEvent(@RequestBody CalendarEventRequest event) {
        return ResponseEntity.ok(calendarEventService.createEvent(event));
    }

    @GetMapping
    @Operation(summary = "모든 일정 이벤트 조회", description = "모든 일정 이벤트 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "이벤트 목록을 성공적으로 조회함")
    public ResponseEntity<List<CalendarEventResponse>> getAllEvents() {
        return ResponseEntity.ok(calendarEventService.getAllEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID로 일정 이벤트 조회", description = "특정 ID로 일정 이벤트를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트를 성공적으로 조회함"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음")
    })
    public ResponseEntity<CalendarEventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarEventService.getEventById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "일정 이벤트 수정", description = "제공된 세부 정보로 기존 일정 이벤트를 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트가 성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음")
    })
    public ResponseEntity<CalendarEventResponse> updateEvent(@PathVariable Long id, @RequestBody CalendarEventRequest updatedEvent) {
        return ResponseEntity.ok(calendarEventService.updateEvent(id, updatedEvent));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "일정 이벤트 삭제", description = "ID로 일정 이벤트를 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "이벤트가 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        calendarEventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 일정을 이메일로 공유합니다.
     *
     * @param id     공유할 일정의 ID
     * @param emails 공유할 이메일 목록
     * @return 공유된 일정 정보
     */
    @PostMapping("/{id}/share")
    @Operation(summary = "일정 이벤트 공유", description = "제공된 이메일 주소로 일정 이벤트를 공유합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트가 성공적으로 공유됨"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 이메일 주소")
    })
    public ResponseEntity<CalendarEventResponse> shareEvent(@PathVariable Long id, @RequestBody List<String> emails) {
        return ResponseEntity.ok(calendarEventService.shareEvent(id, emails));
    }

    /**
     * 일정을 공유한 이메일 목록을 조회합니다.
     *
     * @param id 조회할 일정의 ID
     * @return 공유된 이메일 목록
     */
    @GetMapping("/{id}/shared-users")
    @Operation(summary = "공유된 이메일 조회", description = "이벤트가 공유된 이메일 주소 목록을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공유된 이메일 목록을 성공적으로 조회함"),
            @ApiResponse(responseCode = "404", description = "이벤트를 찾을 수 없음")
    })
    public ResponseEntity<List<String>> getSharedEmails(@PathVariable Long id) {
        return ResponseEntity.ok(calendarEventService.getSharedEmails(id));
    }

}
