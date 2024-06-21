package com.victory.server.component;

import com.victory.server.service.MsgService;
import com.victory.server.worker.MsgWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandLineApp implements CommandLineRunner {

    private final Environment ENV;
    public static Object lock = new Object();

    @Autowired
    MsgService msgService;

    public CommandLineApp(Environment env) {
        ENV = env;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("This is CommandLineApp");
    }

    @Bean(name="executor")
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor){
        return args -> {
            int threadCount = Integer.parseInt(ENV.getProperty("thread.count"));
            log.info("ThreadCount : {}",threadCount);
            synchronized (lock) {
                for (int i=0;i<threadCount;++i){
                    executor.execute(new MsgWorker(msgService,i));
                    System.out.println("Thread Run : "+i);
                }
            }
        };
    }
}
