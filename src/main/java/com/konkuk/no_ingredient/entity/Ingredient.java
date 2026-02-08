package com.konkuk.no_ingredient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // 중복 방지 설정
    private String name;

    private String category;

    @ManyToMany(mappedBy = "ingredients")
    private List<Menu> menus = new ArrayList<>();
}