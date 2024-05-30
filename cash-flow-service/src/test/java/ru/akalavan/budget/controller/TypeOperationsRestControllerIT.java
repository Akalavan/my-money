package ru.akalavan.budget.controller;

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

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TypeOperationsRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/type_operations.sql")
    void findAllTypeOperation_ReturnsTypeOperationList() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cash-flow-api/type-operations");

        //when
        mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {"id": 1, "name": "Операция 1", "description": "Описание 1"},
                                    {"id": 2, "name": "Операция 2", "description": "Описание 2"},
                                    {"id": 3, "name": "Операция 3", "description": "Описание 3"},
                                    {"id": 4, "name": "Операция 4", "description": "Описание 4"}
                                ]
                                """)
                );
    }

    @Test
    void createTypeOperation_RequestIsValid_ReturnsNewTypeOperation() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cash-flow-api/type-operations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Операция 1",
                            "description": "Описание 1"
                        }
                        """);
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/cash-flow-api/type-operation/1"),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "Операция 1",
                                    "description": "Описание 1"
                                }
                                """)
                );
    }

    @Test
    void createTypeOperation_RequestIsInvalid_ReturnsProblemDetails() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cash-flow-api/type-operations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "   ",
                            "description": "Описание 1"
                        }
                        """)
                .locale(Locale.of("ru", "RU"));
        //when
        mockMvc.perform(requestBuilder)
                //then
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
