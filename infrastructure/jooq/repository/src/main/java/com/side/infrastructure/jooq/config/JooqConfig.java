package com.side.infrastructure.jooq.config;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class JooqConfig {

    @Bean
    public DSLContext dslContext(DataSource dataSource) {

        // 2. jOOQ Configuration 설정
        DefaultConfiguration configuration = new DefaultConfiguration();

        // 데이터베이스 연결
        configuration.setDataSource(dataSource);
        // 사용하는 SQL Dialect (e.g., MYSQL, POSTGRES)
        configuration.setSQLDialect(SQLDialect.MYSQL);

        // 3. Optional: Settings 설정 (jOOQ 동작 설정)
        Settings settings = new Settings()
                .withRenderSchema(false) // 스키마 이름 출력 생략
                .withExecuteWithOptimisticLocking(true) // 낙관적 락 사용
                .withRenderFormatted(true); // 쿼리를 포맷된 형태로 출력
        configuration.setSettings(settings);

        // 4. DSLContext 생성
        return DSL.using(configuration);
    }
}
