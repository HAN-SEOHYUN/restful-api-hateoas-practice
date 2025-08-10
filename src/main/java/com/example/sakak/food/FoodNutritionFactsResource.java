package com.example.sakak.food;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class FoodNutritionFactsResource extends EntityModel<FoodNutritionFacts> {

    public FoodNutritionFactsResource(FoodNutritionFacts foodNutritionFacts, Link... links) {
        super(foodNutritionFacts, List.of(links));
        add(linkTo(FoodNutritionFactsController.class).slash(foodNutritionFacts.getNo()).withSelfRel());
    }
}
