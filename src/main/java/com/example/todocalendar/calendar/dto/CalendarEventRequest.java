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
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventRequest {

    // request에서 id는 필요없음 id는 DB에서 자동으로 생성됨 그리고 수정할때는 PathVariable로 받으니 굳이 필요없음
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
    private List<String> sharedWithEmails;

    @Schema(description = "이벤트 상태", example = "SCHEDULED")
    private EventStatus status;

    public CalendarEventEntity toEntity() {
        return CalendarEventEntity.builder()
                .title(title)
                .description(description)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .sharedWithEmails(sharedWithEmails)
                .status(status != null ? status : EventStatus.SCHEDULED)
                .build();
    }

    // 변경이 있는 필드만 업데이트
    public void updateEntity(CalendarEventEntity event) {
        if (title != null) event.setTitle(title);
        if (description != null) event.setDescription(description);
        if (date != null) event.setDate(date);
        if (startTime != null) event.setStartTime(startTime);
        if (endTime != null) event.setEndTime(endTime);
        if (location != null) event.setLocation(location);
        if (sharedWithEmails != null) event.setSharedWithEmails(sharedWithEmails);
        if (status != null) event.setStatus(status);
    }

}
