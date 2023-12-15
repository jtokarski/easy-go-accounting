package org.defendev.easygo.web.config;

import org.defendev.common.time.ClockManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;



@Configuration
public class ClockConfig {

    @Bean
    public ClockManager systemUtcClockManager() {
        return new ClockManager(Clock.systemUTC());
    }

}

