package com.example.sakak.food;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "food_nutrition_facts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "no")
public class FoodNutritionFacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO", nullable = false, updatable = false)
    private Integer no;

    @Column(name = "SAMPLE_ID", columnDefinition = "text")
    private String sampleId;

    @Column(name = "food_code", length = 50)
    private String foodCode;

    @Column(name = "DB군", columnDefinition = "text")
    private String dbGroup;

    @Column(name = "상용제품", columnDefinition = "text")
    private String commercialProduct;

    @Column(name = "food_name", nullable = false, length = 255)
    private String foodName;

    @Column(name = "research_year", length = 4, nullable = false)
    private String researchYear;

    @Column(name = "maker_name", length = 255)
    private String makerName;

    @Column(name = "채취시기", columnDefinition = "text")
    private String collectionTime;

    @Column(name = "식품대분류", columnDefinition = "text")
    private String foodCategory;

    @Column(name = "식품상세분류", columnDefinition = "text")
    private String foodSubCategory;

    @Column(name = "1회제공량", columnDefinition = "text")
    private String servingSize;

    @Column(name = "내용량_단위", columnDefinition = "text")
    private String contentUnit;

    @Column(name = "총내용량(g)", columnDefinition = "text")
    private String totalContentGram;

    @Column(name = "총내용량(mL)", columnDefinition = "text")
    private String totalContentMl;

    @Column(name = "에너지(㎉)", columnDefinition = "text")
    private String energy;

    @Column(name = "수분(g)", columnDefinition = "text")
    private String moisture;

    @Column(name = "단백질(g)", columnDefinition = "text")
    private String protein;

    @Column(name = "지방(g)", columnDefinition = "text")
    private String fat;

    @Column(name = "탄수화물(g)", columnDefinition = "text")
    private String carbohydrate;

    @Column(name = "총당류(g)", columnDefinition = "text")
    private String totalSugar;

}