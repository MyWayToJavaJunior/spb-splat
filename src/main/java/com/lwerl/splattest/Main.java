package com.lwerl.splattest;

import com.lwerl.splattest.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Path;

/**
 * Created by lWeRl on 06.07.2017.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info(System.getProperty("rootpath"));
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
