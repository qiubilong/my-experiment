package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author chenxuegui
 * @since 2025/3/7
 */
public class TestLog {
    
    static Logger logger = LoggerFactory.getLogger("ROOT");

    public static void main(String[] args) {

        logger.info("eeee");


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("end start ...l");
            System.out.println("end start ...1");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("end success ...l");
            System.out.println("end success ...1");
        },"My-ShutdownHook"));


        waitToStop();
    }


    public static void waitToStop(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入字符（输入 'kill' 结束程序）:");
        while (true) {
            String input = scanner.nextLine(); // 读取整行输入
            if (input.equalsIgnoreCase("kill")) { // 不区分大小写匹配退出指令
                break;
            }
        }
        scanner.close(); // 释放资源
        System.exit(0);
    }
}
