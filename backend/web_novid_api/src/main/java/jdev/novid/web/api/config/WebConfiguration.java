package jdev.novid.web.api.config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jdev.novid.foundation.config.ApplicationConfiguration;


@EnableWebMvc
@Import(value = { ApplicationConfiguration.class })
public class WebConfiguration implements WebMvcConfigurer {

    private static final Logger LOG = LogManager.getLogger(WebConfiguration.class);

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        LOG.info("adding http message converters...");
        
        converters.add(new StringHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        
        LOG.info("done adding http message converters...");

    }

}