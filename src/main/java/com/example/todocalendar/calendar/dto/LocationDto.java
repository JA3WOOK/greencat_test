package com.example.todocalendar.calendar.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private String venue;  // 장소명
    private String address;  // 주소
    private String city;  // 도시
    private String state;  // 주 또는 지역
    private String country;  // 국가
}
