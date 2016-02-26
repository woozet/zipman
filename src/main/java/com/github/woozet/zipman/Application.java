package com.github.woozet.zipman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Configuration.class);

        ThreadPoolTaskExecutor taskExecutor = ac.getBean("taskExecutor", ThreadPoolTaskExecutor.class);
        Downloader downloader = ac.getBean("downloader", Downloader.class);

        taskExecutor.execute(downloader);
        taskExecutor.shutdown();

        log.info("Ended main thread.");
    }
}