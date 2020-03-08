package com.example.mockserver;

import javax.naming.ldap.Control;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Logger
@RestController
public class Controllers {


  @Autowired
  RestTemplate restTemplate;

//  Controllers(RestTemplateBuilder restTemplateBuilder){
//    this.restTemplate = restTemplateBuilder.build();
//  }

  @RequestMapping(path = "/", method = RequestMethod.GET)
  public ResponseType get() {
    return restTemplate.getForObject("http://localhost:9999", ResponseType.class);
  }


}
