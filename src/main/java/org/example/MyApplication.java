package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
@MapperScan("org.example.web.dao.mapper")
//@EnableDubbo
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class, args);


        //SpringApplication.exit(applicationContext);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown Hook开始执行...");
            try {
                Thread.sleep(30000); // 等待30秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Shutdown Hook执行完毕");
        }));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("多线程开始执行...");
                try {
                    Thread.sleep(30000); // 等待30秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("多线程执行完毕");
            }
        }).start();
    }

}

/**
 * -Xms1500M -Xmx1500M -Xss256k -XX:SurvivorRatio=6 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
 */