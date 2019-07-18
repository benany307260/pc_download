package com.bentest.spiders.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class DownloadTaskExecutorConfig {
	
	@Autowired
	private SystemConfig systemConfig;

    @Bean
    public TaskExecutor downloadTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(systemConfig.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(systemConfig.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(systemConfig.getMaxPoolSize());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(systemConfig.getKeepAliveSeconds());
        // 设置默认线程名称
        executor.setThreadNamePrefix("downloadTask-");
        // 设置拒绝策略
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}