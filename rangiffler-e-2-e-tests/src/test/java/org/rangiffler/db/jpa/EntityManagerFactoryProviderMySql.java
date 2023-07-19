package org.rangiffler.db.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.rangiffler.db.managerDb.ServiceDB;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.rangiffler.config.ConfigHub.configEnv;

public enum EntityManagerFactoryProviderMySql {
    INSTANCE;
    private Map<ServiceDB, EntityManagerFactory> emfStore = new ConcurrentHashMap<>();

    public EntityManagerFactory getEmf(ServiceDB serviceDB) {
        return emfStore.computeIfAbsent(serviceDB, service -> {
            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.put("hibernate.connection.username", configEnv.dbLogin());
            properties.put("hibernate.connection.password", configEnv.dbPassword());
            properties.put("hibernate.connection.url", service.getJdbcUrl());

            return new ThredLocalEntityManagerFactory(Persistence.createEntityManagerFactory(
                    "rangiffler-persistence-unit-name", properties));
        });
    }
}
