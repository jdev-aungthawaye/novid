package jdev.novid.component.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jdev.novid.component.security.JasyptCrypto;
import jdev.novid.component.util.BeanMapper;
import jdev.novid.component.util.SpringContext;

@Configuration
@EnableAspectJAutoProxy
@PropertySource({ "classpath:/jasypt.properties" })
public class ComponentConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public BeanMapper beanMapper() {

        return new BeanMapper();

    }

    @Bean
    public JasyptCrypto jasyptCrypto() {

        return new JasyptCrypto(this.env.getProperty("jasypt.env.name"));

    }

    @Bean
    public SpringContext springContext() {

        return new SpringContext();

    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {

        return new MethodValidationPostProcessor();

    }

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.findAndRegisterModules();

        return objectMapper;

    }

}
