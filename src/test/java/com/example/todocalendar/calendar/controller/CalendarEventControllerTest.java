package com.example.todocalendar.calendar.controller;

import com.example.todocalendar.calendar.dto.CalendarEventRequest;
import com.example.todocalendar.calendar.dto.CalendarEventResponse;
import com.example.todocalendar.calendar.service.CalendarEventService;
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

@WebMvcTest(CalendarEventController.class)
class CalendarEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarEventService calendarEventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/calendar - 새 이벤트 생성")
    void createEvent() throws Exception {
        CalendarEventRequest request = new CalendarEventRequest(); // 필요한 필드 채워도 되고
        CalendarEventResponse response = CalendarEventResponse.builder().id(1L).build();

        Mockito.when(calendarEventService.createEvent(any(CalendarEventRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/calendar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/calendar - 모든 이벤트 조회")
    void getAllEvents() throws Exception {
        CalendarEventResponse response = CalendarEventResponse.builder().id(1L).build();

        Mockito.when(calendarEventService.getAllEvents()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/calendar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/calendar/{id} - 특정 이벤트 조회")
    void getEventById() throws Exception {
        CalendarEventResponse response = CalendarEventResponse.builder().id(1L).build();

        Mockito.when(calendarEventService.getEventById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/calendar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/calendar/{id} - 이벤트 수정")
    void updateEvent() throws Exception {
        CalendarEventRequest request = new CalendarEventRequest();
        CalendarEventResponse response = CalendarEventResponse.builder().id(1L).build();

        Mockito.when(calendarEventService.updateEvent(eq(1L), any(CalendarEventRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/calendar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/calendar/{id} - 이벤트 삭제")
    void deleteEvent() throws Exception {
        mockMvc.perform(delete("/api/calendar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/calendar/{id}/share - 이벤트 공유")
    void shareEvent() throws Exception {
        List<String> emails = List.of("test@example.com");
        CalendarEventResponse response = CalendarEventResponse.builder().id(1L).build();

        Mockito.when(calendarEventService.shareEvent(eq(1L), any(List.class))).thenReturn(response);

        mockMvc.perform(post("/api/calendar/1/share")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/calendar/{id}/shared-users - 공유된 이메일 조회")
    void getSharedEmails() throws Exception {
        List<String> emails = List.of("test@example.com");

        Mockito.when(calendarEventService.getSharedEmails(1L)).thenReturn(emails);

        mockMvc.perform(get("/api/calendar/1/shared-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("test@example.com"));
    }
}
