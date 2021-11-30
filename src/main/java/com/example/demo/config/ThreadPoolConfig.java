package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Slf4j
public class ThreadPoolConfig {


    @Bean("threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {


        return new ThreadPoolExecutor(20, 100, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200),new  MyThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
               log.warn("被拒绝<<<<<>>>>>>> 活跃数量:{}, 最大线程数:{}, 核心线程数:{},队列数量:{}", executor.getActiveCount(), executor.getMaximumPoolSize(), executor.getCorePoolSize(), executor.getQueue().size());
            }
        });
    }


    @Bean(name = "executorsMonitor")
    public ExecutorsMonitor executorsMonitor(){
        return new ExecutorsMonitor(20,"my-test-pool");
    }
}
@Slf4j
 class MyThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public MyThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "sxr-thread-pool-";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}