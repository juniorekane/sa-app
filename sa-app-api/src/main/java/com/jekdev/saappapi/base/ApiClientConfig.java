package com.jekdev.saappapi.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApiClientConfig {

  @Bean
  RestClient saApiClient() {
    return RestClient.builder().build();
  }
}
