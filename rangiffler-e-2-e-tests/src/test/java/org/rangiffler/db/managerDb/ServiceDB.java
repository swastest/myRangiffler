package org.rangiffler.db.managerDb;


import static org.rangiffler.config.ConfigHub.configEnv;

public enum ServiceDB {
    RANGIFFLER_AUTH("jdbc:mysql://%s/rangiffler_auth"),
    RANGIFFLER_GEO("jdbc:mysql://%s/rangiffler_geo"),
    RANGIFFLER_USER_DATA("jdbc:mysql://%s/rangiffler_users"),
    RANGIFFLER_PHOTO("jdbc:mysql://%s/rangiffler_photo");

    private final String jdbcUrl;

    ServiceDB(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUrl() {
        return String.format(jdbcUrl, configEnv.dbAddress());
    }
}
