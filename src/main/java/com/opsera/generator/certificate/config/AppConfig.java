package com.opsera.generator.certificate.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class AppConfig {

    @Value("${pipeline.config.baseurl}")
    private String pipelineConfigBaseUrl;

    @Value("${kafka.config.baseurl}")
    private String kafkaConfigBaseUrl;

    @Value("${vault.config.baseurl}")
    private String vaultBaseUrl;

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

    /**
     * Stopwatch Bean Creation
     *
     * @return
     */
    @Bean
    public StopWatch stopWatch() {
        return new StopWatch();
    }

    /**
     * Rest Template Bean Creation
     *
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}


