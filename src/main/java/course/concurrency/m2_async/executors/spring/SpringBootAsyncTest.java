package course.concurrency.m2_async.executors.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@SpringBootApplication
@Configuration
public class SpringBootAsyncTest {

    @Autowired
    private AsyncClassTest testClass;

    // this method executes after application start
    @EventListener(ApplicationReadyEvent.class)
    public void actionAfterStartup() throws ExecutionException, InterruptedException {
        testClass.runAsyncTask();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncTest.class, args);
    }

//    @Bean(name = "threadPoolTaskExecutor1")
//    public ThreadPoolTaskExecutor asyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);
//        executor.setMaxPoolSize(4);
//        executor.setQueueCapacity(50);
//        executor.setThreadNamePrefix("AsynchThread::");
//        executor.initialize();
//        return executor;
//    }
}
