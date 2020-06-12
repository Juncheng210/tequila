package com.example.seed.common.config;

import com.example.seed.common.repo.base.IBaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * JPA集成配置 - 数据源2
 *
 * @author Fururur
 * @date 2020/6/9-14:21
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySH",
        transactionManagerRef = "transactionManagerSH",
        basePackages = {"com.example.seed.common.repo.sh"},
        repositoryBaseClass = IBaseRepositoryImpl.class)
public class RepositorySHConfig {
    private final DataSource SHDataSource;
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    public RepositorySHConfig(@Qualifier("SHDataSource") DataSource SHDataSource, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
        this.SHDataSource = SHDataSource;
        this.jpaProperties = jpaProperties;
        this.hibernateProperties = hibernateProperties;
    }

    @Bean(name = "entityManagerFactorySH")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySH(EntityManagerFactoryBuilder builder) {
        // springboot 2.x
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(SHDataSource)
                .properties(properties)
                .packages("com.example.seed.common.entity")
                .persistenceUnit("SHPersistenceUnit")
                .build();
    }

    @Bean(name = "transactionManagerSH")
    public PlatformTransactionManager transactionManagerSH(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySH(builder).getObject());
    }

}
