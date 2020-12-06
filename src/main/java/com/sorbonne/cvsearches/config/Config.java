package com.sorbonne.cvsearches.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.sorbonne.cvsearches.repositories.es")
@EnableScheduling
public class Config {
}
