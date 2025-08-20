package com.example.todocalendar.calendar.dto;

import com.example.todocalendar.calendar.entity.CalendarEventEntity;
import com.example.todocalendar.calendar.entity.EventStatus;
import com.example.todocalendar.calendar.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventResponse {

    @Schema(description = "이벤트 ID", example = "1")
    private Long id;

    @Schema(description = "이벤트 제목", example = "회의")
    private String title;

    @Schema(description = "이벤트 설명", example = "팀 회의")
    private String description;

    @Schema(description = "이벤트 날짜", example = "2023-10-01")
    private LocalDate date;

    @Schema(description = "이벤트 시작 시간", example = "10:00")
    private LocalTime startTime;

    @Schema(description = "이벤트 종료 시간", example = "11:00")
    private LocalTime endTime;

    @Schema(description = "이벤트 장소")
    private Location location;

    @Schema(description = "이벤트 공유 이메일 목록")
    private List<String> sharedWithEmails = new ArrayList<>();

    @Schema(description = "이벤트 상태", example = "SCHEDULED")
    private EventStatus status;

    public static CalendarEventResponse toDto(CalendarEventEntity event) {
        if(event == null) return null;
        return CalendarEventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .sharedWithEmails(event.getSharedWithEmails())
                .status(event.getStatus())
                .build();
    }

}
