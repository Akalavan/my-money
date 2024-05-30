package ru.akalavan.budget.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Интеграционные тесты CategoriesRestController")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CategoriesRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("findAllCategory категории")
    @Sql("/sql/categories.sql")
    void findAllCategory_ReturnsCategoryList() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cash-flow-api/categories");

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {"id": 1, "name": "Категория 1", "description": "Описание 1"},
                                    {"id": 2, "name": "Категория 2", "description": "Описание 2"},
                                    {"id": 3, "name": "Категория 3", "description": "Описание 3"},
                                    {"id": 4, "name": "Категория 4", "description": "Описание 4"}
                                ]
                                """)
                );
    }

    @Test
    @DisplayName("createCategory создаст валидную категорию")
    void createCategory_ReturnIsValid_ReturnsNewCategory() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cash-flow-api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Category", "description": "Category description"}
                        """);

        // when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/cash-flow-api/categories/1"),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name" : "Category",
                                    "description": "Category description"
                                }
                                """)
                );
    }

    @Test
    @DisplayName("createCategory создаст невалидную категорию")
    void createCategory_ReturnIsInvalid_ReturnsProblemDetails() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cash-flow-api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "   ", "description": "Category description"}
                        """)
                .locale(Locale.of("ru", "RU"));

        // when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                    "errors": [
                                        "Название должно быть указано"
                                    ]
                                }
                                """)
                );
    }

}
