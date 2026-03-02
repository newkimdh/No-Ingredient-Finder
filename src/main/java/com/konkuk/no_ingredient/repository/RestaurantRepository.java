package com.konkuk.no_ingredient.repository;

import com.konkuk.no_ingredient.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // JPQL을 이용한 다중 재료 제외 로직
    @Query("SELECT r FROM Restaurant r WHERE NOT EXISTS (" +
            "    SELECT 1 FROM Menu m " +
            "    JOIN m.ingredients i " +
            "    WHERE m.restaurant = r " +
            "    AND i.name IN :excludedIngredients" +
            ")")
    List<Restaurant> findByIngredientsNotIn(@Param("excludedIngredients") List<String> excludedIngredients);
}
