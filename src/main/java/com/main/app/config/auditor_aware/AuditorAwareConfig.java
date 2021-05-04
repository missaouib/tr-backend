package com.main.app.config.auditor_aware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareConfig  {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
