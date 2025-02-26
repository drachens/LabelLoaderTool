package com.marsol.config;

import com.marsol.application.LabelController;
import com.marsol.domain.LabelService;
import com.marsol.domain.ScaleService;
import com.marsol.infrastructure.integration.SyncDataLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {



    @Bean
    public LabelService labelService() {
        return new LabelService();
    }

    @Bean
    public ScaleService scaleService() {
        return new ScaleService();
    }

    @Bean
    public SyncDataLoader syncDataLoader() {
        return new SyncDataLoader();
    }

    @Bean
    LabelController labelController() {
        return new LabelController(labelService(),syncDataLoader(),scaleService());
    }
}
