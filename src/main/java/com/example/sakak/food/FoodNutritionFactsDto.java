package com.example.sakak.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FoodNutritionFactsDto {

    @NotEmpty
    private String foodCode;

    @NotEmpty
    private String foodName;

    @NotEmpty
    private String researchYear;

    private String sampleId;
    private String dbGroup;
    private String commercialProduct;
    private String makerName;
    private String collectionTime;
    private String foodCategory;
    private String foodSubCategory;
    private String servingSize;
    private String contentUnit;
    private String totalContentGram;
    private String totalContentMl;
    private String energy;
    private String moisture;
    private String protein;
    private String fat;
    private String carbohydrate;
    private String totalSugar;
}