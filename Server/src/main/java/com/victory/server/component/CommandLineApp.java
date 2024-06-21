package com.victory.server.component;

import com.victory.server.queue.MsgQueue;
import com.victory.server.service.MsgService;
import com.victory.server.worker.MsgSelectWorker;
import com.victory.server.worker.MsgSendWorker;
import lombok.Data;
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
    MsgQueue queue;

    @Autowired
    MsgService msgService;


    public CommandLineApp(Environment env, MsgQueue queue) {
        ENV = env;
        this.queue = queue;
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

            executor.execute(new MsgSelectWorker(msgService,0,this.queue));

            log.info("SendThreadCount : {}",threadCount);
            synchronized (lock) {
                for (int i=0;i<threadCount;++i){
                    executor.execute(new MsgSendWorker(msgService,i,this.queue));
                    System.out.println("Send Thread Run : "+i);
                }
            }
        };
    }
}
