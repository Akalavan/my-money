package ru.akalavan.budget.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Интеграционные тесты CategoryRestController")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CategoryRestControllerIT {

    @Autowired
    MockMvc mockMvc;

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
