package com.side.infrastructure;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

public class JooqCodeGenerator {
    public static void main(String[] args) throws Exception {
        // JDBC 설정
        Jdbc jdbc = new Jdbc()
                .withDriver("com.mysql.cj.jdbc.Driver")
                .withUrl("jdbc:mysql://localhost:3306/test_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul")
                .withUser("test_id")
                .withPassword("test_pw");

        // 데이터베이스 설정
        Database database = new Database()
                .withName("org.jooq.meta.mysql.MySQLDatabase")
                .withInputSchema("test_db");

        // 타겟 설정 (생성된 코드의 패키지와 디렉토리 지정)
        Target target = new Target()
                .withPackageName("com.side.jooq.generated")
                .withDirectory("com/side/infrastructure/jooq/repository/src/main/java");

        // Generator 설정
        Generator generator = new Generator()
                .withName("org.jooq.codegen.JavaGenerator")
                .withDatabase(database)
                .withTarget(target)
                .withGenerate(new Generate().withJavaTimeTypes(true));


        // 최종 jOOQ 구성 객체 생성
        Configuration configuration = new Configuration()
                .withJdbc(jdbc)
                .withGenerator(generator);

        // 코드 생성 실행
        GenerationTool.generate(configuration);
    }
}

