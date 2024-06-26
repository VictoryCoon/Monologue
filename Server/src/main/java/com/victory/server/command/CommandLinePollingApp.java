package com.victory.server.command;

import com.victory.server.queue.MsgQueue;
import com.victory.server.service.MsgService;
import com.victory.server.worker.MsgSelectWorker;
import com.victory.server.worker.MsgSendWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandLinePollingApp implements CommandLineRunner {

    private final Environment ENV;
    public static Object lock = new Object();

    @Autowired
    final MsgQueue pollingQueue;

    @Autowired
    MsgService msgService;


    public CommandLinePollingApp(Environment env, MsgQueue pollingQueue) {
        ENV = env;
        this.pollingQueue = pollingQueue;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("This is CommandLine[Polling]App");
    }

    @Bean(name="pollingExecutor")
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }


    @Bean
    public CommandLineRunner pollingRunner(TaskExecutor executor){
        return args -> {
            int threadCount = Integer.parseInt(ENV.getProperty("thread.count"));
            // DB Polling Selector
            executor.execute(new MsgSelectWorker(msgService,0,this.pollingQueue));

            // DB Polling Executor
            synchronized (this.pollingQueue) {
                for (int i=0;i<threadCount;++i){
                    executor.execute(new MsgSendWorker(msgService,i,this.pollingQueue));
                    //System.out.println("Send Thread Run : "+i);
                }
            }
        };
    }
}
