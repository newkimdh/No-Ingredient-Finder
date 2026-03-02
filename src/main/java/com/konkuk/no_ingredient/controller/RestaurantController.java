package com.konkuk.no_ingredient.controller;

import com.konkuk.no_ingredient.dto.RestaurantResponseDTO;
import com.konkuk.no_ingredient.entity.Restaurant;
import com.konkuk.no_ingredient.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // 모든 식당 조회
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.findAllRestaurant();
    }

    // 식당 등록
    @PostMapping
    public Restaurant register(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    // 식당 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }

    /**
     * /api/restaurants/search 로 기피 재료 리스트를 보냄
     * */
    @PostMapping("/search")
    // 기존: public List<Restaurant> searchRestaurants(...)
    // 변경:
    public List<RestaurantResponseDTO> searchRestaurants(@RequestBody List<String> excludedIngredients) {
        return restaurantService.getFilteredRestaurants(excludedIngredients);
    }
}
