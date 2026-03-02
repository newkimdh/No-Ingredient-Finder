package com.konkuk.no_ingredient.service;

import com.konkuk.no_ingredient.dto.RestaurantResponseDTO;
import com.konkuk.no_ingredient.entity.Restaurant;
import com.konkuk.no_ingredient.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Transactional  // 쓰기 작업이므로 readOnly 옵션 off
    public Restaurant save(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional  // 이하 동문
    public void delete(@PathVariable Long id) {
        restaurantRepository.deleteById(id);
    }

    // 기존: public List<Restaurant> getFilteredRestaurants(List<String> excludedIngredients)..
    // 변경:
    public List<RestaurantResponseDTO> getFilteredRestaurants(List<String> excludedIngredients) {

        List<Restaurant> restaurants;

        if (excludedIngredients == null || excludedIngredients.isEmpty()) {
            restaurants = restaurantRepository.findAll();
        }
        else {
            restaurants = restaurantRepository.findByIngredientsNotIn(excludedIngredients);
        }

        // return restaurantRepository.findByIngredientsNotIn(excludedIngredients);
        return restaurants.stream()
                .map(RestaurantResponseDTO::from)
                .collect(Collectors.toList());
    }
}
