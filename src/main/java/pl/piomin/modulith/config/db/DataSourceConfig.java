package pl.piomin.modulith.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value("${db.url-master}")
    private String urlMaster;
    @Value("${db.username-master}")
    private String usernameMaster;
    @Value("${db.password-master}")
    private String passwordMaster;

    @Value("${db.url-slave}")
    private String urlSlave;
    @Value("${db.username-slave}")
    private String usernameSlave;
    @Value("${db.password-slave}")
    private String passwordSlave;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProp.put("hibernate.hbm2ddl.auto", "none");
        hibernateProp.put("hibernate.show_sql", true);
        return hibernateProp;
    }

    private DataSource writeDataSource() {
        return createDataSource(DbType.MASTER);
    }

    private DataSource readDataSource() {
        return createDataSource(DbType.SLAVE);
    }

    @Bean
    public RoutingDataSource routingDataSource() {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        DataSource writeDataSource = writeDataSource();
        DataSource readDataSource = readDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DbType.MASTER, writeDataSource);
        dataSourceMap.put(DbType.SLAVE, readDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") RoutingDataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    private DataSource createDataSource(DbType dbType) {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(driverClassName);

            if(DbType.MASTER.equals(dbType)) {
                hikariConfig.setJdbcUrl(urlMaster);
                hikariConfig.setUsername(usernameMaster);
                hikariConfig.setPassword(passwordMaster);
            }
            else {
                hikariConfig.setJdbcUrl(urlSlave);
                hikariConfig.setUsername(usernameSlave);
                hikariConfig.setPassword(passwordSlave);
            }

            hikariConfig.setMaximumPoolSize(5);
            hikariConfig.setConnectionTestQuery("SELECT 1");
            hikariConfig.setPoolName("springHikariCP");

            hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
            hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

            return new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            return null;
        }
    }
}
