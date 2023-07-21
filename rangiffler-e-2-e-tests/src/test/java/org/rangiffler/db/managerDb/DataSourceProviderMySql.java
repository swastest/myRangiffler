package org.rangiffler.db.managerDb;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.rangiffler.config.ConfigHub.configEnv;

public enum DataSourceProviderMySql {
    INSTANCE;
    private Map<ServiceDB, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    public DataSource getDataSource(ServiceDB serviceDB) {
        return dataSourceMap.computeIfAbsent(serviceDB, service -> {
            MysqlDataSource mySqlDataSource = new MysqlDataSource();
            mySqlDataSource.setUrl(service.getJdbcUrl());
            mySqlDataSource.setUser(configEnv.dbLogin());
            mySqlDataSource.setPassword(configEnv.dbPassword());
            return mySqlDataSource;
        });
    }
}
