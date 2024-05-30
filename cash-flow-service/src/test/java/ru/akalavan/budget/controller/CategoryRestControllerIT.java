package ru.akalavan.budget.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Интеграционные тесты CategoryRestController")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CategoryRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/categories.sql")
    @DisplayName("findCategory успешно находит категорию")
    void findCategory_CategoryExists_ReturnCategory() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cash-flow-api/categories/1");

        // when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "Категория 1",
                                    "description": "Описание 1"
                                }
                                """)
                );
    }

    @Test
    @DisplayName("findCategory не находит категорию")
    void findCategory_CategoryDoesNotExists_ReturnNotFound() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cash-flow-api/categories/1");

        // when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    @Sql("/sql/categories.sql")
    void updateCategory_RequestIsValid_ReturnsNoContent() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/cash-flow-api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Category",
                            "description": "Category description"
                        }
                        """);
        //when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(status().isNoContent());
    }

    @Test
    @Sql("/sql/categories.sql")
    void updateCategory_RequestIsInvalid_ReturnsBadRequest() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/cash-flow-api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "  ",
                            "description": "Category description"
                        }
                        """)
                .locale(Locale.of("ru","RU"));
        //when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                    "errors": ["Название должно быть указано"]
                                }
                                """)
                );
    }

    @Test
    void updateCategory_CategoryDoesNotExists_ReturnNoFound() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/cash-flow-api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Category",
                            "description": "Category description"
                        }
                        """)
                .locale(Locale.of("ru","RU"));
        //when
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(status().isNotFound());
    }

    @Test
    @DisplayName("deleteCategory удалить категорию")
    @Sql("/sql/categories.sql")
    void deleteCategory_CategoryExists_ReturnNoContent() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/cash-flow-api/categories/1");

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    @DisplayName("deleteCategory не найдёт категорию")
    @Sql("/sql/categories.sql")
    void deleteCategory_CategoryDoesNotExists_ReturnNoFound() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/cash-flow-api/categories/5");

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }
}
