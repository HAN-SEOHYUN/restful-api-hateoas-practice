package com.example.sakak.food;

import com.example.sakak.common.ErrorsResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_VALUE)
public class FoodNutritionFactsController {

    private final FoodNutritionFactsService foodNutritionFactsService;

    public FoodNutritionFactsController(FoodNutritionFactsService foodNutritionFactsService) {
        this.foodNutritionFactsService = foodNutritionFactsService;
    }

    @PostMapping
    public ResponseEntity createFoodNutritionFacts(@RequestBody @Valid FoodNutritionFactsDto foodNutritionFactsDto,
                                                   Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        FoodNutritionFacts newFoodNutritionFacts = foodNutritionFactsService.createFoodNutritionFacts(foodNutritionFactsDto, errors);
        if (newFoodNutritionFacts == null) {
            return badRequest(errors);
        }

        var selfLinkBuilder = linkTo(FoodNutritionFactsController.class).slash(newFoodNutritionFacts.getNo());
        URI createdUri = selfLinkBuilder.toUri();
        FoodNutritionFactsResource foodNutritionFactsResource = new FoodNutritionFactsResource(newFoodNutritionFacts);
        foodNutritionFactsResource.add(linkTo(FoodNutritionFactsController.class).withRel("query-food-nutrition-facts"));
        foodNutritionFactsResource.add(selfLinkBuilder.withRel("update-food-nutrition-facts"));
        foodNutritionFactsResource.add(Link.of("/docs/index.html#resources-food-nutrition-facts-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(foodNutritionFactsResource);
    }

    @GetMapping
    public ResponseEntity queryFoodNutritionFacts(Pageable pageable,
                                                  PagedResourcesAssembler<FoodNutritionFacts> assembler) {
        Page<FoodNutritionFacts> page = this.foodNutritionFactsService.findAll(pageable);
        var pagedResources = assembler.toModel(page, e -> new FoodNutritionFactsResource(e));
        pagedResources.add(Link.of("/docs/index.html#resources-food-nutrition-facts-list").withRel("profile"));
        pagedResources.add(linkTo(FoodNutritionFactsController.class).withRel("create-food-nutrition-facts"));
        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{no}")
    public ResponseEntity getFoodNutritionFacts(@PathVariable Integer no) {
        FoodNutritionFacts foodNutritionFacts = this.foodNutritionFactsService.findById(no);
        FoodNutritionFactsResource foodNutritionFactsResource = new FoodNutritionFactsResource(foodNutritionFacts);
        foodNutritionFactsResource.add(Link.of("/docs/index.html#resources-food-nutrition-facts-get").withRel("profile"));
        foodNutritionFactsResource.add(linkTo(FoodNutritionFactsController.class).slash(foodNutritionFacts.getNo()).withRel("update-food-nutrition-facts"));
        return ResponseEntity.ok(foodNutritionFactsResource);
    }

    @PutMapping("/{no}")
    public ResponseEntity updateFoodNutritionFacts(@PathVariable Integer no,
                                                   @RequestBody @Valid FoodNutritionFactsDto foodNutritionFactsDto,
                                                   Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        FoodNutritionFacts updatedFoodNutritionFacts = this.foodNutritionFactsService.updateFoodNutritionFacts(no, foodNutritionFactsDto, errors);
        if (updatedFoodNutritionFacts == null) {
            return badRequest(errors);
        }

        FoodNutritionFactsResource foodNutritionFactsResource = new FoodNutritionFactsResource(updatedFoodNutritionFacts);
        foodNutritionFactsResource.add(Link.of("/docs/index.html#resources-food-nutrition-facts-update").withRel("profile"));
        return ResponseEntity.ok(foodNutritionFactsResource);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity deleteFoodNutritionFacts(@PathVariable Integer no) {
        this.foodNutritionFactsService.deleteById(no);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity searchFoodNutritionFacts(
            @RequestParam(required = false) String foodName,
            @RequestParam(required = false) String researchYear,
            @RequestParam(required = false) String makerName,
            @RequestParam(required = false) String foodCode,
            Pageable pageable,
            PagedResourcesAssembler<FoodNutritionFacts> assembler
    ) {
        var cond = FoodNutritionFactsSearchCondition.builder()
                .foodName(foodName)
                .researchYear(researchYear)
                .makerName(makerName)
                .foodCode(foodCode)
                .build();

        Page<FoodNutritionFacts> page = this.foodNutritionFactsService.search(cond, pageable);

        var pagedResources = assembler.toModel(page, FoodNutritionFactsResource::new);
        pagedResources.add(Link.of("/docs/index.html#resources-food-nutrition-facts-search").withRel("profile"));
        pagedResources.add(linkTo(FoodNutritionFactsController.class).withRel("create-food-nutrition-facts"));

        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        ErrorsResource body = ErrorsResource.from(errors); // ★ DTO로 변환
        body.add(Link.of("/docs/index.html#errors").withRel("profile"));
        return ResponseEntity.badRequest().body(body);
    }
}