package com.konkuk.no_ingredient.service;

import com.konkuk.no_ingredient.dto.RestaurantResponseDTO;
import com.konkuk.no_ingredient.entity.Ingredient;
import com.konkuk.no_ingredient.entity.Restaurant;
import com.konkuk.no_ingredient.repository.IngredientRepository;
import com.konkuk.no_ingredient.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final IngredientRepository ingredientRepository;
    private final RestaurantRepository restaurantRepository;

//    public RestaurantService(IngredientRepository ingredientRepository, RestaurantRepository restaurantRepository) {
//        this.ingredientRepository = ingredientRepository;
//        this.restaurantRepository = restaurantRepository;
//    }

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

        // 1. 존재하지 않는 재료가 있는지 검증(예를 들어, "오이" 대신 "오잉"을 입력한 경우 처리)
        if (excludedIngredients != null || !excludedIngredients.isEmpty()) {
            for (String name : excludedIngredients) {
                // DB에 해당 이름의 재료가 있는지 확인
                if (!ingredientRepository.existsByName(name)) {
                    // 존재하지 않으면 throw Error
                    throw new IllegalArgumentException("존재하지 않는 재료가 포함되어 있습니다.: " + name);
                }
            }
        }

        List<Restaurant> restaurants;

        if (excludedIngredients == null || excludedIngredients.isEmpty()) {
            restaurants = restaurantRepository.findAll();
        }
        else {
            restaurants = restaurantRepository.findByIngredientsNotIn(excludedIngredients);
        }

        // 2. 결과가 없는 경우 처리
        if (restaurants == null || restaurants.isEmpty()) {
            throw new NoSuchElementException("조건에 맞는 식당이 없습니다.");
        }

        // return restaurantRepository.findByIngredientsNotIn(excludedIngredients);
        return restaurants.stream()
                .map(RestaurantResponseDTO::from)
                .collect(Collectors.toList());
    }
}
