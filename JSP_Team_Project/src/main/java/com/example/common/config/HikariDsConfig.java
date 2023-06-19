package com.example.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
public final class HikariDsConfig {
    private final String CLASSNAME = "org.mariadb.jdbc.Driver";
    private final String JDBC_URL = "jdbc:mariadb://localhost:3306/culture_db";
    private final String USERNAME = "root";
    private final String PASSWORD = "12345";
    private final String CACHE_PREP_STMTS = "true";
    private HikariDataSource ds;
    private HikariConfig config;

    public DataSource config() {
        /* HikariCP 로드 */
        config = new HikariConfig();

        config.setDriverClassName(CLASSNAME);
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", CACHE_PREP_STMTS);
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
        log.info("성공:" + ds);

        return ds;
    }
}
