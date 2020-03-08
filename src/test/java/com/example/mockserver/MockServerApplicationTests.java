package com.example.mockserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class MockServerApplicationTests {

  RestTemplate testRestTemplate;

  @Autowired
  RestTemplateBuilder builder;

  @BeforeEach
  void init() {
    testRestTemplate = builder.build();
  }

  @Test
  void contextLoads() throws JsonProcessingException {
    ResponseType actual = testRestTemplate.getForObject("http://localhost:8080", ResponseType.class);
    String actualJson = new ObjectMapper().writeValueAsString(actual);
    ResponseType responseType = new ResponseType();
    responseType.something = "whatever";
    String expected = new ObjectMapper().writeValueAsString(responseType);
    Assertions.assertEquals(expected, actualJson);
  }

}
