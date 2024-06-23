package com.github.lucitacastello.proposta_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // mapeando tudo o que vem depois de localhost
                .allowedOrigins("http://localhost") //tudo o que for localhost
                .allowedMethods("*"); //todos os métodos HTTP
    }
}
