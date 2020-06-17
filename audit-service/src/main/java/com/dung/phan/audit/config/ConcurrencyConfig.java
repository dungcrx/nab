package com.dung.phan.audit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
@Configuration
public class ConcurrencyConfig {
    public static final String TASK_EXECUTOR ="audit-task";
    private static final int THREAD_POOLS=20;


    @Bean(TASK_EXECUTOR)
    public Executor originationThreadPoolTaskExecutor() {
        Executor executor = Executors.newFixedThreadPool(THREAD_POOLS);
        return executor;
    }

}
