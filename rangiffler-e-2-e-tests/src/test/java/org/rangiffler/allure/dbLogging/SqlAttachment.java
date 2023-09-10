package org.rangiffler.allure.dbLogging;

import io.qameta.allure.attachment.AttachmentData;

public class SqlAttachment implements AttachmentData {
    private final String name;
    private final String sql;
    private final String statement;

    public SqlAttachment(String name, String sql, String statement) {
        this.name = name;
        this.sql = sql;
        this.statement = statement;
    }

    @Override
    public String getName() {
        return null;
    }
    public String getSql() {
        return sql;
    }

    public String getStatement() {
        return statement;
    }
}
