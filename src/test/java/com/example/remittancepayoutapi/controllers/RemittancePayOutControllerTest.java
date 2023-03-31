package com.example.remittancepayoutapi.controllers;

import com.example.remittancepayoutapi.data.SampleData;
import com.example.remittancepayoutapi.dto.RequestDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:env.properties")
class RemittancePayOutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RequestDto requestBody;

    private Faker faker;

    @BeforeEach
    void setup() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        faker = new Faker(new Locale("en-NG"));
        requestBody = objectMapper.readValue(SampleData.jsonData, RequestDto.class);
    }

    @Test
    @DisplayName("Checking the user payout")
    void payoutTest() throws Exception {
        requestBody.getTransaction().setReference(faker.bothify("########??", true));
        mockMvc.perform(post("/payout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", Matchers.is("00")))
                .andExpect(jsonPath("$.message", Matchers.is("Successful")))
                .andExpect(jsonPath("$.transaction.reference", Matchers.is(requestBody.getTransaction().getReference())));
    }

    @Test
    @DisplayName("checking the user payout status")
    void payoutStatusTest() throws Exception {
        String reference = "94377829";
        mockMvc.perform(get("/payout/status?reference=" + reference)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", Matchers.is("00")))
                .andExpect(jsonPath("$.message", Matchers.is("Successful")))
                .andExpect(jsonPath("$.transaction.reference", Matchers.is(reference)));
    }

    @Test
    @DisplayName("when user fills the request then create a payout")
    void createPayoutTest() throws Exception {
        requestBody.getTransaction().setReference(RandomStringUtils.randomAlphanumeric(8));
        mockMvc.perform(post("/payout/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", Matchers.is("00")))
                .andExpect(jsonPath("$.message", Matchers.is("Successful")))
                .andExpect(jsonPath("$.transaction.reference", Matchers.is(requestBody.getTransaction().getReference())));
    }
}