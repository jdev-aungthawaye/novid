package jdev.novid.web.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jdev.novid.model.query.UserQuery;
import jdev.novid.web.api.security.ApiAuthenticationEntryPoint;
import jdev.novid.web.api.security.ApiAuthenticationTokenFilter;
import jdev.novid.web.api.security.ApiAuthenticator;
import jdev.novid.web.api.security.Authenticator;
import jdev.novid.web.api.security.ExceptionHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("jdev.novid")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserQuery userQuery;

    @Bean
    public ApiAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {

        return new ApiAuthenticationTokenFilter(this.authenticator());

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // we don't need CSRF because our token is invulnerable
        // httpSecurity.requiresChannel().anyRequest().requiresSecure();

        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler())
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll().antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").hasRole("USER")
                .anyRequest().authenticated();

        // custom token based security filter
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandler(), ApiAuthenticationTokenFilter.class);
        // disable page caching
        httpSecurity.headers().cacheControl().disable();

    }

    @Bean
    public ExceptionHandler exceptionHandler() {

        return new ExceptionHandler();

    }

    @Bean
    public Authenticator authenticator() {

        return new ApiAuthenticator(this.userQuery);

    }

    @Bean
    public ApiAuthenticationEntryPoint unauthorizedHandler() {

        return new ApiAuthenticationEntryPoint();

    }

}
