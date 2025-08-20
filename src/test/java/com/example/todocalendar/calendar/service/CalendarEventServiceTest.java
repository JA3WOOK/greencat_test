package com.example.todocalendar.calendar.service;

import com.example.todocalendar.calendar.dto.CalendarEventRequest;
import com.example.todocalendar.calendar.dto.CalendarEventResponse;
import com.example.todocalendar.calendar.entity.CalendarEventEntity;
import com.example.todocalendar.calendar.repository.CalendarEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CalendarEventServiceTest {

    @Mock
    private CalendarEventRepository calendarEventRepository;

    @InjectMocks
    private CalendarEventService calendarEventService;

    private CalendarEventEntity seed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seed = CalendarEventEntity.builder()
                .id(1L)
                .date(LocalDate.of(2025, 5, 1))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(11, 0))
                .build();

        when(calendarEventRepository.findAll()).thenReturn(List.of(seed));
        when(calendarEventRepository.findById(1L)).thenReturn(Optional.of(seed));
        when(calendarEventRepository.save(any(CalendarEventEntity.class))).thenReturn(seed);
    }

    @Test
    @DisplayName("새 이벤트 생성")
    void createEvent() {
        CalendarEventRequest request = new CalendarEventRequest();

        CalendarEventResponse result = calendarEventService.createEvent(request);

        assertThat(result.getId()).isEqualTo(1L);
    }

@Test
@DisplayName("모든 이벤트 조회")
void getAllEvents() {
    List<CalendarEventResponse> events = calendarEventService.getAllEvents();

    assertThat(events).hasSize(1);
    assertThat(events.get(0).getId()).isEqualTo(1L);
}

    @Test
    @DisplayName("ID로 이벤트 조회")
    void getEventById() {
        CalendarEventResponse result = calendarEventService.getEventById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이벤트 수정")
    void updateEvent() {
        CalendarEventRequest request = new CalendarEventRequest();

        CalendarEventResponse result = calendarEventService.updateEvent(1L, request);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이벤트 삭제")
    void deleteEvent() {
        calendarEventService.deleteEvent(1L);

        verify(calendarEventRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("이벤트 공유")
    void shareEvent() {
        CalendarEventResponse result = calendarEventService.shareEvent(1L, List.of("test@example.com"));

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("공유된 이메일 조회")
    void getSharedEmails() {
        when(calendarEventRepository.findById(1L))
                .thenReturn(Optional.of(seed.toBuilder().sharedWithEmails(List.of("test@example.com")).build()));

        List<String> emails = calendarEventService.getSharedEmails(1L);

        assertThat(emails).containsExactly("test@example.com");
    }
}
