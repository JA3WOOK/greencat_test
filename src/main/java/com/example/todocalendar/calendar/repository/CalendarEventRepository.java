package com.example.todocalendar.calendar.repository;

import com.example.todocalendar.calendar.entity.CalendarEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarEventRepository extends JpaRepository<CalendarEventEntity, Long> {
}
