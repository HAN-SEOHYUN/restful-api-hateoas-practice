package com.example.sakak.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoodNutritionFactsRepository extends JpaRepository<FoodNutritionFacts, Integer>, JpaSpecificationExecutor<FoodNutritionFacts> {
}