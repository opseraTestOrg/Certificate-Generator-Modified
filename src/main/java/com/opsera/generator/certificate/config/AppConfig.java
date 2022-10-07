package com.opsera.generator.certificate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class AppConfig {

    @Value("${pipeline.config.baseurl}")
    private String pipelineConfigBaseUrl;


    /**
     * Factory Bean
     *
     * @return
     */
    @Bean
    public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(IServiceFactory.class);
        return factoryBean;
    }


}


