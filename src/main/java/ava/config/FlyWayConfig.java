package ava.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlyWayConfig {
    @Bean(initMethod = "migrate")
    public Flyway flyway(@Autowired DataSource dataSource) {
        return Flyway.configure().dataSource(dataSource).load();
    }
}
