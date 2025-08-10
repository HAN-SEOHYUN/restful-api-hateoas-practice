package com.example.sakak.food;

import lombok.Builder;

@Builder
public record FoodNutritionFactsSearchCondition(
        String foodName,
        String researchYear,
        String makerName,
        String foodCode
) {}