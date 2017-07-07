package com.lwerl.splattest.config;

import com.lwerl.splattest.exception.IncorrectPropertyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class AppConfig {

    @Bean
    public Path rootPath() throws IOException {
        String rootPathString = System.getenv("SPLAT_ROOT_PATH");
        Path rootPath = Paths.get(rootPathString == null ? "" : rootPathString);
        if (Files.exists(rootPath)) {
            if (Files.isDirectory(rootPath)) {
                return rootPath.toAbsolutePath();
            } else {
                throw new IncorrectPropertyException();
            }
        } else {
            Files.createDirectory(rootPath);
            return rootPath.toAbsolutePath();
        }
    }
}
