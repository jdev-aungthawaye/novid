package jdev.novid.integration.client.smspoh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import jdev.novid.component.security.JasyptCrypto;

@Configuration
@ComponentScan("jdev.novid.integration.client.smspoh")
@PropertySource(value = { "classpath:/smspoh.properties" })
public class SmsPohConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public SmsPohClient smsPohClient() {

        return new SmsPohClient(this.env.getRequiredProperty("smspoh.senderName"));

    }

    @Bean
    public SmsPohServiceContext smsPohServiceContext(JasyptCrypto crypto) {

        return new SmsPohServiceContext(this.env.getRequiredProperty("smspoh.baseUrl"),
                crypto.decrypt(this.env.getRequiredProperty("smspoh.token")));

    }

}