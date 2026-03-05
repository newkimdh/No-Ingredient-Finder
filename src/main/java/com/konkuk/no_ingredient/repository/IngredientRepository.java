package com.konkuk.no_ingredient.repository;

import com.konkuk.no_ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 입력한 재료 이름이 존재하는지 확인하는 쿼리 Method 추가
    boolean existsByName(String name);
}
