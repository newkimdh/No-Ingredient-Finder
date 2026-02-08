package com.konkuk.no_ingredient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.List;
import java.util.ArrayList;

@Entity // 데이터베이스 테이블과 1대1 대응됨
@Table(name = "Restaurant")
@Getter // Lombok: Getter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 생성

public class Restaurant {
    @Id // 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 연동
    private Long id;      // MySQL의 BIGINT

    @Column(nullable = false, length = 100) // NOT NULL 속성
    private String name;  // MySQL의 VARCHAR(length)

    @Column(length = 255)
    private String address;

    @Column(length = 50)
    private String category;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();
}
