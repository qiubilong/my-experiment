package org.example.mongo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Lists;
import org.example.web.dao.mongo.LanguageBR;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

   static String rootPath = "C:\\git";

    @Test
    public void doLangUpdate(){
        String appName = "C:\\git\\overseas-activities";
        File rootFile = new File(appName);
        File[] listFiles = rootFile.listFiles();
        if(listFiles == null){
            return;
        }
        for (File appFile : listFiles) {
            if(shouldSkip(appFile)){
                continue;
            }
            String langPath = appFile.getAbsolutePath() +"\\src\\main\\resources\\i18n\\messages.properties";
            String langPathTo = appFile.getAbsolutePath() +"\\src\\main\\resources\\i18n\\messages_pt_BR.properties";
            System.out.println(langPath);
            try {
                List<String> keys = new ArrayList<>(200);
                Files.lines(Paths.get(langPath)).forEach(message->{
                    //System.out.println(message);
                    Pair<String, String> keyValuePair = pairKeyValue(message);
                    if(keyValuePair != null){
                        keys.add(keyValuePair.getKey());
                    }
                });
                if (!keys.isEmpty()) {
                    FileWriter fileTo = new FileWriter (langPathTo,false);
                    Map<String, LanguageBR> messageLangFromDb = getMessageLangFromDb(keys);
                    for (String key : keys) {
                        LanguageBR languageBR = messageLangFromDb.get(key);
                        if(languageBR ==null){
                            continue;
                        }
                        String line = key +"=" + languageBR.getPt_BR()+"\n";
                        fileTo.write(line);
                    }
                    fileTo.flush();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String,LanguageBR> getMessageLangFromDb(List<String> keys){
        Criteria cri = Criteria.where("key").in(keys);
        List<LanguageBR> languageBRS = mongoTemplate.find(new Query().addCriteria(cri), LanguageBR.class,"LanguageBR2");
        return languageBRS.stream().collect(Collectors.toMap(LanguageBR::getKey, v->v));
    }

    public Pair<String,String> pairKeyValue(String message){
        String[] split = message.split("=");
        if(split.length<2){
            return null;
        }
        return Pair.of(split[0],split[1]);
    }

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
