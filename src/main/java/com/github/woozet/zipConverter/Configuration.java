package com.github.woozet.zipConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by 1001195 on 16. 2. 26..
 */
public class Configuration {
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public Downloader downloader() {
        Downloader downloader = new Downloader();
        downloader.setUrl("http://www.epost.go.kr/search/areacd/areacd_chgaddr_DB.zip");
//        downloader.setUrl("http://www.epost.go.kr/search/areacd/zipcode_DB.zip");
        downloader.setPath("downloads/db.zip");

        return downloader;
    }
}
