package com.sugon.analysis.configs.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @program: analysis->SecondaryConfig
 * @description:
 * @author: gaowh
 * @create: 2019-09-26 10:28
 **/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryTertiary",
        transactionManagerRef = "transactionManagerTertiary",
        basePackages = {"com.sugon.analysis.repository.sugon.oracle","com.sugon.analysis.platform.repository"}) //设置Repository所在位置
public class TertiaryConfig {

    @Autowired
    @Qualifier("tertiaryDataSource")
    private DataSource tertiaryDataSource;

    @Resource
    private JpaProperties jpaProperties;

    @Bean(name = "entityManagerTertiary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryTertiary(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryTertiary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryTertiary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(tertiaryDataSource)
                .properties(getVendorProperties(tertiaryDataSource))
                .packages("com.sugon.analysis.domain.data.object.sugon.oracle","com.sugon.analysis.platform.entity") //设置实体类所在位置
                .persistenceUnit("tertiaryPersistenceUnit")
                .build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "transactionManagerTertiary")
    PlatformTransactionManager transactionManagerTertiary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryTertiary(builder).getObject());
    }
}