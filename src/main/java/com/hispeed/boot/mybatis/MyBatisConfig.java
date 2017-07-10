package com.hispeed.boot.mybatis;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wangtt on 2017-3-28.
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {
    private static Logger log = LoggerFactory.getLogger(MyBatisConfig.class);

    @Resource
    private DataSource dataSource;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
            bean.setConfigLocation(resolver.getResource("classpath:/mybatis-config.xml"));
            return bean.getObject();
        } catch (Exception e) {
            log.error("boot init sqlSessionFactory error:"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionInterceptor transactionInterceptor(DataSourceTransactionManager transactionManager) {
        TransactionInterceptor ti = new TransactionInterceptor();
        ti.setTransactionManager(annotationDrivenTransactionManager());
        Properties properties = new Properties();
        properties.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("get*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Throwable");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Throwable");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Throwable");
        ti.setTransactionAttributes(properties);
        return ti;
    }

    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
        transactionAutoProxy.setProxyTargetClass(false);
        transactionAutoProxy.setBeanNames(new String[] { "*ServiceImpl" });
        transactionAutoProxy.setInterceptorNames(new String[] { "transactionInterceptor" });
        return transactionAutoProxy;
    }
}
