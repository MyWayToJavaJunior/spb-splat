package com.lwerl.splattest.config;

import com.lwerl.splattest.exception.IncorrectPropertyException;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
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

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    private DataSource dataSource() {
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(100);
        ds.setDataSourceClassName("org.postgresql.Driver");
        ds.addDataSourceProperty("url", "URL");
        ds.addDataSourceProperty("user", "USER");
        ds.addDataSourceProperty("password", "PASSWORD");
        ds.addDataSourceProperty("cachePrepStmts", true);
        ds.addDataSourceProperty("prepStmtCacheSize", 250);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        ds.addDataSourceProperty("useServerPrepStmts", true);
        return ds;
    }

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        //... (Dialect, 2nd level entity cache, query cache, etc.)
        return properties;
    }
}
