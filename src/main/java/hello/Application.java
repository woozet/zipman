package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        StaticApplicationContext ac = new StaticApplicationContext();

        RootBeanDefinition taskExecutorDef = new RootBeanDefinition(ThreadPoolTaskExecutor.class);
        MutablePropertyValues teProperties = taskExecutorDef.getPropertyValues();
        teProperties.add("corePoolSize", 5);
        teProperties.add("maxPoolSize", 10);
        teProperties.add("WaitForTasksToCompleteOnShutdown", true);

        RootBeanDefinition downloaderDef = new RootBeanDefinition(Downloader.class);
        downloaderDef.setScope("prototype");
        MutablePropertyValues pvProperties = downloaderDef.getPropertyValues();
        pvProperties.add("url", "http://www.epost.go.kr/search/areacd/areacd_chgaddr_DB.zip");
        pvProperties.add("path", "downloads/db.zip");
//      pvProperties.add("url", "http://www.epost.go.kr/search/areacd/zipcode_DB.zip");

        ac.registerBeanDefinition("taskExecutor", taskExecutorDef);
        ac.registerBeanDefinition("downloader", downloaderDef);

        ThreadPoolTaskExecutor taskExecutor = ac.getBean("taskExecutor", ThreadPoolTaskExecutor.class);
        Downloader downloader = ac.getBean("downloader", Downloader.class);

        taskExecutor.execute(downloader, 1);
        taskExecutor.shutdown();

        log.info("Ended main thread.");
    }
}