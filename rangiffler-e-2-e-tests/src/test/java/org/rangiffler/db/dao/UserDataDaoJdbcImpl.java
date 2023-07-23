package org.rangiffler.db.dao;

import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.db.managerDb.DataSourceProviderMySql;
import org.rangiffler.db.managerDb.ServiceDB;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

public class UserDataDaoJdbcImpl implements UserDataDao {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public UserDataDaoJdbcImpl() {
        DataSourceTransactionManager transactionManager = new JdbcTransactionManager(DataSourceProviderMySql.INSTANCE.getDataSource(ServiceDB.RANGIFFLER_USER_DATA));
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
    }

    @Override
    public int deleteUser(UserDataEntity user) {
        UUID userId = user.getId();
        byte[] userIdBytes = jdbcTemplate.queryForObject("SELECT UUID_TO_BIN(?)", byte[].class, userId.toString());
        return transactionTemplate.execute(status -> {
            jdbcTemplate.update("DELETE FROM friends WHERE user_id =? OR friend_id =?", userIdBytes, userIdBytes);
            return jdbcTemplate.update("DELETE FROM users WHERE id =?", userIdBytes);
        });
    }

    @Override
    public UserDataEntity userInfoByUserName(String username) {
        UserDataEntity user = new UserDataEntity();
        jdbcTemplate.query("SELECT * FROM users WHERE username = ?",
                rs -> {
                    user.convertSetId((byte[]) rs.getObject("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFirstname(rs.getString("firstname"));
                    user.setLastname(rs.getString("lastname"));
                    user.setAvatar(rs.getBytes("avatar"));
                }, username);
        return user;
    }
}
