package com.konkuk.no_ingredient.repository;

import com.konkuk.no_ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
