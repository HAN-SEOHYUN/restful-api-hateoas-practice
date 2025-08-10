package com.example.sakak.food;

import com.example.sakak.common.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(FoodNutritionFactsController.class)
@AutoConfigureRestDocs(
        outputDir = "build/generated-snippets",
        uriScheme = "http",
        uriHost = "98.83.105.239",
        uriPort = 8080
)
@Import({GlobalExceptionHandler.class})
class FoodNutritionFactsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    FoodNutritionFactsService foodNutritionFactsService;

    private String createRequestJson() {
        return """
        {
          "sampleId": "D000008-66-AVG",
          "foodCode": "D000008",
          "dbGroup": "과일",
          "commercialProduct": "유기농사과",
          "foodName": "맛있는사과",
          "researchYear": "2025",
          "makerName": "사과농장",
          "collectionTime": "가을",
          "foodCategory": "과일류",
          "foodSubCategory": "사과",
          "servingSize": "120",
          "contentUnit": "g",
          "totalContentGram": "240",
          "totalContentMl": "",
          "energy": "60",
          "moisture": "84.5",
          "protein": "0.4",
          "fat": "0.3",
          "carbohydrate": "15.0",
          "totalSugar": "11.0"
        }
        """;
    }

    private FoodNutritionFacts sampleEntity() {
        return sampleEntity(1);
    }

    private FoodNutritionFacts sampleEntity(int no) {
        return FoodNutritionFacts.builder()
                .no(no)
                .sampleId("D000009-ZZ-AVG")
                .foodCode("D000009")
                .dbGroup("과일")
                .commercialProduct("유기농사과")
                .foodName("맛있는사과-" + no)
                .researchYear("2025")
                .makerName("사과농장")
                .collectionTime("가을")
                .foodCategory("과일류")
                .foodSubCategory("사과")
                .servingSize("120")
                .contentUnit("g")
                .totalContentGram("240")
                .totalContentMl("")
                .energy("60")
                .moisture("84.5")
                .protein("0.4")
                .fat("0.3")
                .carbohydrate("15.0")
                .totalSugar("11.0")
                .build();
    }

    @Test
    @DisplayName("POST /api - FoodNutritionFacts 생성")
    void createFood() throws Exception {
        // given
        when(foodNutritionFactsService.createFoodNutritionFacts(any(), any()))
                .thenReturn(sampleEntity());

        // when & then
        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(createRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.no").value(1))
                .andDo(document("food-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedRequestFields(
                                fieldWithPath("sampleId").description("시료 ID"),
                                fieldWithPath("foodCode").description("식품 코드"),
                                fieldWithPath("dbGroup").description("DB 그룹"),
                                fieldWithPath("commercialProduct").description("상용 제품명"),
                                fieldWithPath("foodName").description("식품명"),
                                fieldWithPath("researchYear").description("연구 연도"),
                                fieldWithPath("makerName").description("제조사"),
                                fieldWithPath("collectionTime").description("채취 시기"),
                                fieldWithPath("foodCategory").description("식품 대분류"),
                                fieldWithPath("foodSubCategory").description("식품 상세 분류"),
                                fieldWithPath("servingSize").description("1회 제공량"),
                                fieldWithPath("contentUnit").description("내용량 단위"),
                                fieldWithPath("totalContentGram").description("총 내용량(g)"),
                                fieldWithPath("totalContentMl").description("총 내용량(mL)"),
                                fieldWithPath("energy").description("에너지(㎉)"),
                                fieldWithPath("moisture").description("수분(g)"),
                                fieldWithPath("protein").description("단백질(g)"),
                                fieldWithPath("fat").description("지방(g)"),
                                fieldWithPath("carbohydrate").description("탄수화물(g)"),
                                fieldWithPath("totalSugar").description("총 당류(g)")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 리소스의 URI")
                        ),
                        links(
                                linkWithRel("self").description("현재 리소스에 대한 URI"),
                                linkWithRel("query-food-nutrition-facts").description("음식 정보 목록 조회"),
                                linkWithRel("update-food-nutrition-facts").description("음식 정보 수정"),
                                linkWithRel("profile").description("API 문서를 설명하는 링크")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("no").description("식별자"),
                                subsectionWithPath("_links").description("해당 리소스와 관련된 링크 모음. 이 링크들을 통해 다음 가능한 행동들을 수행할 수 있습니다.")
                        )
                ));
    }

    @Test
    @DisplayName("GET /api - 목록 페이지 조회")
    void listFoods() throws Exception {
        Page<FoodNutritionFacts> page = new PageImpl<>(
                List.of(sampleEntity(1), sampleEntity(2)),
                PageRequest.of(0, 2),
                10
        );
        when(foodNutritionFactsService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api?page=0&size=10&sort=no,asc") // ← 쿼리 파라미터 직접 작성
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.profile.href").exists())
                .andExpect(jsonPath("$._links['create-food-nutrition-facts'].href").exists())
                .andDo(document("food-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("GET /api/{no} - 단건 조회")
    void getFood() throws Exception {
        when(foodNutritionFactsService.findById(1)).thenReturn(sampleEntity());

        mockMvc.perform(get("/api/{no}", 1)
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links['update-food-nutrition-facts'].href").exists())
                .andExpect(jsonPath("$._links.profile.href").exists())
                .andDo(document("food-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(
                                linkWithRel("self").description("현재 리소스에 대한 URI"),
                                linkWithRel("update-food-nutrition-facts").description("음식 정보 수정"),
                                linkWithRel("profile").description("API 문서를 설명하는 링크")
                        )
                ));
    }

    @Test
    @DisplayName("PUT /api/{no} - 수정")
    void updateFood() throws Exception {
        when(foodNutritionFactsService.updateFoodNutritionFacts(eq(1), any(), any()))
                .thenReturn(sampleEntity());

        mockMvc.perform(put("/api/{no}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(createRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(1))
                .andExpect(jsonPath("$._links.profile.href").exists())
                .andDo(document("food-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedRequestFields(
                                fieldWithPath("foodCode").description("식품 코드"),
                                fieldWithPath("foodName").description("식품명"),
                                fieldWithPath("researchYear").description("연구 연도"),
                                fieldWithPath("sampleId").description("샘플 아이디"),
                                fieldWithPath("dbGroup").description("데이터베이스 그룹"),
                                fieldWithPath("commercialProduct").description("상품명"),
                                fieldWithPath("makerName").description("제조사"),
                                fieldWithPath("collectionTime").description("수집 시기"),
                                fieldWithPath("foodCategory").description("식품 대분류"),
                                fieldWithPath("foodSubCategory").description("식품 소분류"),
                                fieldWithPath("servingSize").description("1회 제공량"),
                                fieldWithPath("contentUnit").description("단위"),
                                fieldWithPath("totalContentGram").description("총 내용량(g)"),
                                fieldWithPath("energy").description("에너지(kcal)"),
                                fieldWithPath("moisture").description("수분(g)"),
                                fieldWithPath("protein").description("단백질(g)"),
                                fieldWithPath("fat").description("지방(g)"),
                                fieldWithPath("carbohydrate").description("탄수화물(g)"),
                                fieldWithPath("totalSugar").description("총 당류(g)")
                        ),
                        relaxedResponseFields(
                                subsectionWithPath("_links").description("해당 리소스와 관련된 링크 모음. 이 링크들을 통해 다음 가능한 행동들을 수행할 수 있습니다.")
                        )
                ));
    }

    @Test
    @DisplayName("DELETE /api/{no} - 삭제")
    void deleteFood() throws Exception {
        doNothing().when(foodNutritionFactsService).deleteById(1);

        mockMvc.perform(delete("/api/{no}", 1))
                .andExpect(status().isNoContent())
                .andDo(document("food-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("GET /api/search - 검색")
    void search() throws Exception {
        Page<FoodNutritionFacts> page = new PageImpl<>(
                List.of(
                        sampleEntity(1),
                        sampleEntity(2)
                ),
                PageRequest.of(0, 2),
                2
        );
        when(foodNutritionFactsService.search(any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/search?foodName=사과&researchYear=2025&makerName=사과농장&foodCode=D000009&page=0&size=2")
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._embedded.*[0].foodName").value("맛있는사과-1"))
                .andExpect(jsonPath("$._embedded.*[1].foodName").value("맛있는사과-2"))
                .andExpect(jsonPath("$._links.profile.href").exists())
                .andDo(document("food-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedResponseFields(
                                subsectionWithPath("_embedded.foodNutritionFactsList[]._links.self.href")
                                        .description("해당 식품 리소스의 self 링크"),

                                subsectionWithPath("_links.self.href")
                                        .description("현재 리소스에 대한 URI"),
                                subsectionWithPath("_links.profile.href")
                                        .description("API 문서를 설명하는 링크"),

                                subsectionWithPath("page.size").description("페이지당 요소 개수"),
                                subsectionWithPath("page.totalElements").description("총 요소 개수"),
                                subsectionWithPath("page.totalPages").description("총 페이지 수"),
                                subsectionWithPath("page.number").description("현재 페이지 번호(0부터 시작)")
                        )
                ));
    }
}
