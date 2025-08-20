package com.example.todocalendar.calendar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable  // 이 클래스가 다른 엔티티에 임베디드 객체로 사용될 것임을 표시
public class Location {

    @Column(name = "venue")
    private String venue;  // 장소명

    @Column(name = "address")
    private String address;  // 주소

    @Column(name = "city")
    private String city;  // 도시

    @Column(name = "state")
    private String state;  // 주 또는 지역

    @Column(name = "country")
    private String country;  // 국가
}
