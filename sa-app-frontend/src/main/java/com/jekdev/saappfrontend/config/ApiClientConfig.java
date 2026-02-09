package com.jekdev.saappfrontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Spring configuration for frontend HTTP clients.
 *
 * <p>Defines the {@link RestClient} used by frontend services to communicate with the backend API module.
 */
@Configuration
public class ApiClientConfig {

  @Bean
  RestClient saApiClient() {
    return RestClient.builder().baseUrl("http://localhost:8080").build();
  }
}
