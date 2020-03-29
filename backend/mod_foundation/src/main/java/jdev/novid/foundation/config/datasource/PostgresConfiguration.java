package jdev.novid.foundation.config.datasource;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jdev.novid.component.security.JasyptCrypto;

@Configuration
@PropertySource(value = { "classpath:/postgres_jdbc.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "jdev" })
public class PostgresConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private JasyptCrypto jasyptCrypto;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(env.getRequiredProperty("jdbc.db.url"));
        config.setUsername(this.jasyptCrypto.decrypt(env.getRequiredProperty("jdbc.db.username")));
        config.setPassword(this.jasyptCrypto.decrypt(env.getRequiredProperty("jdbc.db.password")));
        config.setDriverClassName(env.getRequiredProperty("jdbc.db.driver"));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(this.dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        entityManagerFactoryBean.setPackagesToScan("jdev.**");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("jdbc.hibernate.dialect"));
        jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("jdbc.hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("jdbc.hibernate.format_sql"));

        jpaProperties.put("hibernate.default_schema", env.getRequiredProperty("jdbc.hibernate.default_schema"));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
