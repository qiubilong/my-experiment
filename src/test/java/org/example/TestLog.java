package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenxuegui
 * @since 2025/3/7
 */
public class TestLog {
    
    static Logger logger = LoggerFactory.getLogger("ROOT");

    public static void main(String[] args) {

        logger.info("eeee");

    }
}
