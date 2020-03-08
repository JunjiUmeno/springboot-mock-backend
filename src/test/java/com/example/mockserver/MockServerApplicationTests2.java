package com.example.mockserver;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class MockServerApplicationTests2 {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  TestRestTemplate testRestTemplate;


  @BeforeEach
  void init() {
  }

  @Test
  void contextLoads() throws JsonProcessingException {
    MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    mockServer.expect(ExpectedCount.once(), requestTo("http://localhost:9999"))
        .andExpect(method(HttpMethod.GET))
    .andRespond(withSuccess("{ \"something\" : \"whatever\"}", MediaType.APPLICATION_JSON));

    ResponseType actual = testRestTemplate
        .getForObject("http://localhost:8080", ResponseType.class);

    mockServer.verify();

    String actualJson = new ObjectMapper().writeValueAsString(actual);
    ResponseType responseType = new ResponseType();
    responseType.something = "whatever";
    String expected = new ObjectMapper().writeValueAsString(responseType);
    Assertions.assertEquals(expected, actualJson);
  }


}
