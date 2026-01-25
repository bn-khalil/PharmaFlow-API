package com.pharmaflow.demo.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    // this bean here for Asynchronous notifications and create a new tread
    // to execute tasks in parallel this configs will limit and control
    // since there is big problem if letting @Async with default that lead to
    // create new thread for each function calls with new configs the number of
    // threads are limited but working with efeciant way

    @Bean
    public Executor notificationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("noty-thread-");
        executor.initialize();
        return executor;
    }
}
