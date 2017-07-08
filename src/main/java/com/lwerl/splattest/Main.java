package com.lwerl.splattest;

import com.lwerl.splattest.config.AppConfig;
import com.lwerl.splattest.model.File;
import com.lwerl.splattest.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info(System.getProperty("rootpath"));
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        FileRepository fileRepository = ctx.getBean("fileRepositoryImpl", FileRepository.class);
        File file = fileRepository.get(1L);
        File file1 = new File();
        file1.setName("new");
        file1.setParent(file);
        fileRepository.create(file1);
    }
}
