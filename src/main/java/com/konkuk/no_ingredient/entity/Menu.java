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

public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;

    // 1:N 관계 설정 (Restaurant - Menu)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id") // DB의 FK column 이름
    private Restaurant restaurant;

    // N:M 관계 설정 (Menu - Ingredient)
    @ManyToMany
    @JoinTable(
            name = "Menu_Ingredient", // DB의 매핑 테이블명
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();
}
