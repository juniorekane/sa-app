package com.jekdev.saappfrontend.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring configuration for Thymeleaf template rendering.
 *
 * <p>Registers a template engine with the layout dialect enabled.
 */
@Configuration
public class ThymeleafConfig {

  /**
   * Creates the shared Thymeleaf template engine.
   *
   * @param templateResolver configured template resolver
   * @return configured {@link SpringTemplateEngine}
   */
  @Bean
  public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    templateEngine.addDialect(new LayoutDialect());
    return templateEngine;
  }
}
