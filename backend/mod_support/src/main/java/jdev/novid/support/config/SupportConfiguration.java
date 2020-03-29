package jdev.novid.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jdev.novid.support.helper.SmsPohSmsSender;
import jdev.novid.support.helper.SmsSender;

@Configuration
public class SupportConfiguration {

    @Bean
    public SmsSender smsPohSmsSender() {

        return new SmsPohSmsSender();

    }

}
