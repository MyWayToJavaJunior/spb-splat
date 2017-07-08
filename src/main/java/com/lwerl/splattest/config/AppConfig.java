package com.lwerl.splattest.config;

import com.lwerl.splattest.exception.IncorrectPropertyException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
//@EnableWebMvc
@ComponentScan("com.lwerl.splattest.repository")
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
        sessionFactory.setPackagesToScan("com.lwerl.splattest.model");
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
        final DataSource ds = new DataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/splat");
        ds.setUsername("splat");
        ds.setPassword("splat");
        return ds;
    }

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        //... (Dialect, 2nd level entity cache, query cache, etc.)
        properties.setProperty(SHOW_SQL, "true");
        properties.setProperty(FORMAT_SQL, "true");
        properties.setProperty(USE_SQL_COMMENTS, "true");
        properties.setProperty(ENABLE_LAZY_LOAD_NO_TRANS, "true");
        return properties;
    }
}
