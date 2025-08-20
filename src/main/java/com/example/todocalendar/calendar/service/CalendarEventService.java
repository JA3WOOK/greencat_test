package com.example.todocalendar.calendar.service;

import com.example.todocalendar.calendar.dto.CalendarEventRequest;
import com.example.todocalendar.calendar.dto.CalendarEventResponse;
import com.example.todocalendar.calendar.entity.CalendarEventEntity;
import com.example.todocalendar.calendar.entity.EventStatus;
import com.example.todocalendar.calendar.repository.CalendarEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;

    // 중복되는 메소드는 private 메소드로 빼놓고 재사용하는게 좋음
    private CalendarEventEntity getEventEntity(Long id) {
        return calendarEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id=" + id));
    }

    /**
     * 이벤트의 상태를 확인하고 필요한 경우 업데이트
     * 종료 시간이 지난 이벤트는 COMPLETED 상태로 변경
     *
     * @param event 상태를 확인할 이벤트
     * @return 상태가 업데이트된 이벤트
     */
    private CalendarEventEntity updateEventStatus(CalendarEventEntity event) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventEndDateTime = LocalDateTime.of(event.getDate(), event.getEndTime());

        // 이벤트가 이미 취소된 경우 상태를 변경하지 않음
        if (event.getStatus() == EventStatus.CANCELLED) {
            return event;
        }

        // 현재 시간이 이벤트 종료 시간 이후인 경우 COMPLETED로 상태 변경
        if (now.isAfter(eventEndDateTime) && event.getStatus() != EventStatus.COMPLETED) {
            event.setStatus(EventStatus.COMPLETED);
            return calendarEventRepository.save(event);
        }

        // 현재 시간이 이벤트 시작 시간과 종료 시간 사이인 경우 IN_PROGRESS로 상태 변경
        LocalDateTime eventStartDateTime = LocalDateTime.of(event.getDate(), event.getStartTime());
        if (now.isAfter(eventStartDateTime) && now.isBefore(eventEndDateTime)
                && event.getStatus() == EventStatus.SCHEDULED) {
            event.setStatus(EventStatus.IN_PROGRESS);
            return calendarEventRepository.save(event);
        }

        return event;
    }

    /**
     * @Transactional 어노테이션:
     * - 메소드가 종료될 때 자동으로 커밋 수행
     * - 트랜잭션이 없으면 API 실패 시에도 데이터가 저장될 수 있음
     * <p>
     * 조회 메소드:
     * - readOnly=true로 설정하여 성능 최적화
     * - 등록, 수정 메소드에 readOnly=true 설정 시 예외 발생할 수 있으니깐 주의
     * <p>
     * 엔티티 -> DTO 변환:
     * - 엔티티 직접 노출 방지
     * - 계층간 의존성 분리
     */
    @Transactional
    public List<CalendarEventResponse> getAllEvents() {
        return calendarEventRepository.findAll()
                .stream()
                .map(this::updateEventStatus)  // 상태 업데이트
                .map(CalendarEventResponse::toDto)
                .toList();
    }

    @Transactional
    public CalendarEventResponse getEventById(Long id) {
        CalendarEventEntity entity = getEventEntity(id);
        entity = updateEventStatus(entity);  // 상태 업데이트
        return CalendarEventResponse.toDto(entity);
    }

    @Transactional
    public CalendarEventResponse createEvent(CalendarEventRequest event) {
        CalendarEventEntity savedEntity = calendarEventRepository.save(event.toEntity());
        return CalendarEventResponse.toDto(savedEntity);
    }

    // update 메소드에서 엔티티를 직접 수정하는 방식은 좋지 않음
    // 그리고 DTO -> Entity 변환하는 메소드를 따로 만들어서 사용해야함 (팩토리 메소드라고 하는데 DTO에서 엔티티를 생성하는 메소드임)
    @Transactional
    public CalendarEventResponse updateEvent(Long id, CalendarEventRequest updatedEvent) {
        CalendarEventEntity event = getEventEntity(id);
        updatedEvent.updateEntity(event);

        calendarEventRepository.save(event);
        return CalendarEventResponse.toDto(event);
    }

    // 엔티티를 조작할때는 무조건 트랜잭션을 걸어야하고 존재여부 확인하고 삭제해야함
    @Transactional
    public void deleteEvent(Long id) {
        CalendarEventEntity entity = getEventEntity(id);
        calendarEventRepository.deleteById(entity.getId());
    }

    /**
     * 일정을 이메일로 공유합니다.
     * 실제 이메일 전송은 구현하지 않고, 공유 이력만 저장합니다.
     *
     * @param id 공유할 일정의 ID
     * @param emails 공유할 이메일 목록
     * @return 공유된 일정 정보
     */
    @Transactional
    public CalendarEventResponse shareEvent(Long id, List<String> emails) {
        CalendarEventEntity event = getEventEntity(id);

        // 상태 업데이트
        event = updateEventStatus(event);

        // 이메일 목록이 null인 경우 초기화
        if (event.getSharedWithEmails() == null) {
            event.setSharedWithEmails(new ArrayList<>());
        }

        // 중복 이메일 제거 후 추가
        for (String email : emails) {
            if (!event.getSharedWithEmails().contains(email)) {
                event.getSharedWithEmails().add(email);
            }
        }

        // 저장 및 응답 반환
        CalendarEventEntity savedEvent = calendarEventRepository.save(event);
        return CalendarEventResponse.toDto(savedEvent);
    }

    /**
     * 일정을 공유한 이메일 목록을 조회합니다.
     *
     * @param id 조회할 일정의 ID
     * @return 공유된 이메일 목록
     */
    @Transactional(readOnly = true)
    public List<String> getSharedEmails(Long id) {
        CalendarEventEntity event = getEventEntity(id);
        return event.getSharedWithEmails() != null ? event.getSharedWithEmails() : new ArrayList<>();
    }

}
