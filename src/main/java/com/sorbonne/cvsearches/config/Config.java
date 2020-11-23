package com.sorbonne.cvsearches.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.sorbonne.cvsearches.repositories.es")
public class Config {
}
