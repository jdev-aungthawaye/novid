package jdev.novid.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import jdev.novid.integration.client.smspoh.SmsPohClient;
import jdev.novid.support.helper.SmsPohSmsSender;
import jdev.novid.support.helper.SmsSender;

@Configuration
@ComponentScan("jdev.novid.support")
public class SupportConfiguration {

    @Bean
    public SmsSender smsPohSmsSender(SmsPohClient smsPohClient) {

        return new SmsPohSmsSender(smsPohClient);

    }

}
