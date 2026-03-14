package com.konkuk.no_ingredient.dto;

import com.konkuk.no_ingredient.entity.Menu;
import com.konkuk.no_ingredient.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String category;
    private List<String> menuNames; // 메뉴 객체 대신 이름만 리스트로 담기

    // Entity 를 DTO 로 변환하는 정적 팩토리 메서드
    public static RestaurantResponseDTO from(Restaurant restaurant) {
        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCategory(),
                restaurant.getMenus().stream()
                        .map(Menu::getName)
                        .collect(Collectors.toList())
        );
    }
}

// 확인
