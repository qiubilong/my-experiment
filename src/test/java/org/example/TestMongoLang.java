package org.example;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.example.web.dao.mongo.LanguageBR;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenxuegui
 * @since 2025/3/14
 */
@SpringBootTest
@Slf4j
public class TestMongoLang {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Map<String,Boolean> keysMap = new HashMap<>(10000);

    private static List<LanguageBR> languageBRs = new ArrayList<>(10000);

    private static AtomicInteger count = new AtomicInteger(0);


    @Test
    public void collectAllKeysMain(){
        collectAllKeys("C:\\git");
        collectAllKeys("C:\\git\\overseaslive");

        System.out.println(count.get());

        if(!languageBRs.isEmpty()){
            mongoTemplate.dropCollection(LanguageBR.class);
            mongoTemplate.insertAll(languageBRs);
        }
    }

    public static void collectAllKeys(String rootPath) {
        File rootFile = new File(rootPath);
        for (File file : Objects.requireNonNull(rootFile.listFiles())) {//项目
            if(shouldSkip(file)){
                continue;
            }
            if("C:\\git".equals(rootPath) && file.getName().equals("overseaslive")){
                continue;
            }

            File[] listFiles = file.listFiles();
            if(listFiles == null){
                continue;
            }
            for (File appFile : listFiles) {//项目模块
                if(shouldSkip(appFile)){
                    continue;
                }

                String langPath = appFile.getAbsolutePath() +"\\src\\main\\resources\\i18n\\messages.properties";
                //System.out.println(langPath);
                try {
                    Files.lines(Paths.get(langPath)).forEach(v->{

                        String[] split = v.split("=");
                        if(split.length !=2){
                            return;
                        }
                        System.out.println(v);

                        String key = split[0];
                        String msg = split[1];
                        if(keysMap.containsKey(key)){
                            return;
                        }

                        languageBRs.add(new LanguageBR(key,msg));
                        keysMap.put(key,true);
                        count.incrementAndGet();

                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean shouldSkip(File file){
        if(!file.getName().startsWith("overseas")){
            return true;
        }
        if(file.getName().endsWith("api")){
            return true;
        }


        if(file.getName().contains("pay-process")){
            return true;
        }

        if(file.getName().endsWith("console")){
            return true;
        }


        if(file.getName().endsWith("component")){
            return true;
        }
        if(file.getName().endsWith("overseas-parent")){
            return true;
        }
        if(file.getName().endsWith("overseas-common")){
            return true;
        }

        if(file.getName().contains("gateway")){
            return true;
        }

        if((file.getName().endsWith("biz") || file.getName().endsWith("home")|| file.getName().endsWith("process"))){
            return false;
        }

        if(!file.isDirectory()){
            return true;
        }
        return false;
    }
}
