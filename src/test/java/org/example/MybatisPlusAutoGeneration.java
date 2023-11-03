package org.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


public class MybatisPlusAutoGeneration {

    static final String url = "jdbc:mysql://localhost:3306/experiment?useSSL=false&autoReconnect=true&characterEncoding=utf8";
    static final String username = "root";
    static final String password = "123456";

    public static void main(String[] args) {

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期的类型 TIME_PACK=LocalDateTime;ONLY_DATE=Date;
                            .outputDir(System.getProperty("user.dir") + "/target");

                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                  builder.addInclude("sys_oper_user_log")
                          .entityBuilder()
                          .enableTableFieldAnnotation()
                          .enableChainModel().
                          enableLombok();

                })
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder->{
                    builder.controller("").service("").xml("").serviceImpl("");
                })
                .execute();
    }
}
