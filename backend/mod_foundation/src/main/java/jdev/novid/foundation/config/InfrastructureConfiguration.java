package jdev.novid.foundation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import jdev.novid.foundation.config.datasource.PostgresConfiguration;

@Configuration
@Import(value = { PostgresConfiguration.class })
public class InfrastructureConfiguration {

}
