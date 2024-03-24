package org.scottg.branch.homework.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableCaching
public class appconfig {

    @Bean
    public ThreadPoolTaskExecutor getTaskExecutor(){
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(5);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(120);
        return executor;
    }
}
