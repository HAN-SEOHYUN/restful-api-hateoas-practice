package com.example.sakak.food;

import com.example.sakak.common.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@Transactional
public class FoodNutritionFactsService {

    private final FoodNutritionFactsRepository foodNutritionFactsRepository;
    private final ModelMapper modelMapper;
    private final FoodNutritionFactsValidator foodNutritionFactsValidator;

    public FoodNutritionFactsService(FoodNutritionFactsRepository foodNutritionFactsRepository,
                                     ModelMapper modelMapper,
                                     FoodNutritionFactsValidator foodNutritionFactsValidator) {
        this.foodNutritionFactsRepository = foodNutritionFactsRepository;
        this.modelMapper = modelMapper;
        this.foodNutritionFactsValidator = foodNutritionFactsValidator;
    }

    public FoodNutritionFacts createFoodNutritionFacts(FoodNutritionFactsDto foodNutritionFactsDto, Errors errors) {
        foodNutritionFactsValidator.validate(foodNutritionFactsDto, errors);
        if (errors.hasErrors()) {
            return null;
        }
        FoodNutritionFacts foodNutritionFacts = modelMapper.map(foodNutritionFactsDto, FoodNutritionFacts.class);
        return foodNutritionFactsRepository.save(foodNutritionFacts);
    }

    @Transactional(readOnly = true)
    public FoodNutritionFacts findById(Integer no) {
        return foodNutritionFactsRepository.findById(no)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID(" + no + ")를 가진 FoodNutritionFacts를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<FoodNutritionFacts> findAll(Pageable pageable) {
        return foodNutritionFactsRepository.findAll(pageable);
    }

    public FoodNutritionFacts updateFoodNutritionFacts(Integer no, FoodNutritionFactsDto foodNutritionFactsDto, Errors errors) {
        FoodNutritionFacts existing = foodNutritionFactsRepository.findById(no)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID(" + no + ")를 가진 FoodNutritionFacts를 찾을 수 없습니다."));

        foodNutritionFactsValidator.validate(foodNutritionFactsDto, errors);
        if (errors.hasErrors()) {
            return null;
        }

        modelMapper.map(foodNutritionFactsDto, existing);
        return foodNutritionFactsRepository.save(existing);
    }

    public void deleteById(Integer no) {
        if (!foodNutritionFactsRepository.existsById(no)) {
            throw new EntityNotFoundException("해당 ID(" + no + ")를 가진 FoodNutritionFacts를 찾을 수 없어 삭제할 수 없습니다.");
        }
        foodNutritionFactsRepository.deleteById(no);
    }

    @Transactional(readOnly = true)
    public Page<FoodNutritionFacts> search(FoodNutritionFactsSearchCondition cond, Pageable pageable) {
        Specification<FoodNutritionFacts> spec = Specification.where(null);

        if (cond.foodName() != null && !cond.foodName().isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("foodName")), "%" + cond.foodName().toLowerCase() + "%"));
        }
        if (cond.researchYear() != null && !cond.researchYear().isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("researchYear"), cond.researchYear()));
        }
        if (cond.makerName() != null && !cond.makerName().isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("makerName")), "%" + cond.makerName().toLowerCase() + "%"));
        }
        if (cond.foodCode() != null && !cond.foodCode().isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("foodCode"), cond.foodCode()));
        }

        return foodNutritionFactsRepository.findAll(spec, pageable);
    }
}