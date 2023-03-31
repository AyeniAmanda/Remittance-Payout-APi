package com.example.remittancepayoutapi.controllers;

import com.example.remittancepayoutapi.data.SampleData;
import com.example.remittancepayoutapi.dto.ValidateRequestDto;
import com.example.remittancepayoutapi.enums.Operation;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config.properties")
class CustomerValidatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ValidateRequestDto requestBody;

    @BeforeEach
    void setup() throws JsonProcessingException {
        objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        requestBody = objectMapper.readValue(SampleData.jsonData, ValidateRequestDto.class);
    }

    @Test
    @DisplayName("when user enters the correct requirements then validate user")
    void CustomerValidateTest() throws Exception {
        requestBody.getOrder().setAmount(BigDecimal.valueOf(40.00));
        requestBody.getSource().setOperation(Operation.account_enquiry);

        ResultActions resultActions = mockMvc.perform(post("/customer/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", Matchers.is("00")))
                .andExpect(jsonPath("$.message", Matchers.is("Successful")));
        resultActions.andReturn();
    }
}
