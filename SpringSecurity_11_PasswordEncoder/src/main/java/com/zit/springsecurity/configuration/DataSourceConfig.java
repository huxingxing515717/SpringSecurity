package com.zit.springsecurity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
	@Bean(name="myDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource MyDataSource() {
        return DataSourceBuilder.create().build();
    }
}
