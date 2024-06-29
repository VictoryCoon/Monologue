package com.victory.server.command;

import com.victory.server.queue.MsgQueue;
import com.victory.server.service.MsgService;
import com.victory.server.listener.TcpListener;
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
public class CommandLineListenerApp implements CommandLineRunner {
    private final Environment ENV;
    public static Object lock = new Object();

    @Autowired
    final MsgQueue tcpQueue;

    @Autowired
    MsgService msgService;

    public CommandLineListenerApp(Environment env, MsgQueue tcpQueue) {
        ENV = env;
        this.tcpQueue = tcpQueue;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("This is CommandLine[Listener]App");
    }

    @Primary
    @Bean(name="listeningExecutor")
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }
    @Bean
    public CommandLineRunner listeningRunner(TaskExecutor executor) {
        return args -> {
            int bufferSize = Integer.parseInt(ENV.getProperty("buffer.size"));
            //executor.execute(new TcpListener(this.tcpQueue,bufferSize));
        };
    }
}
