package org.rangiffler.db.dao;

import org.rangiffler.db.entity.auth.Authority;
import org.rangiffler.db.entity.auth.AuthorityEntity;
import org.rangiffler.db.entity.auth.UserAuthEntity;
import org.rangiffler.db.managerDb.DataSourceProviderMySql;
import org.rangiffler.db.managerDb.ServiceDB;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserAuthDaoJdbcImpl implements UserAuthDao {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public UserAuthDaoJdbcImpl() {
        DataSourceTransactionManager transactionManager = new JdbcTransactionManager(DataSourceProviderMySql.INSTANCE.getDataSource(ServiceDB.RANGIFFLER_AUTH));
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
    }

    @Override
    public int createUser(UserAuthEntity user) {
        return 0;
    }

    @Override
    public int updateUser(UserAuthEntity user) {
        return 0;
    }

    @Override
    public void deleteUser(UserAuthEntity user) {
        UUID userId = user.getId();
        byte[] userIdBytes = jdbcTemplate.queryForObject("SELECT UUID_TO_BIN(?)", byte[].class, userId.toString());

        transactionTemplate.execute(status -> {
            jdbcTemplate.update("DELETE FROM authorities WHERE user_id =?", userIdBytes);
            return jdbcTemplate.update("DELETE FROM users WHERE id =?", userIdBytes);
        });
    }

    @Override
    public UserAuthEntity userInfo(String userName) {
        UserAuthEntity user = new UserAuthEntity();
        jdbcTemplate.query("SELECT * FROM users WHERE username = ?",
                rs -> {
                    user.convertSetId((byte[]) rs.getObject("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEnabled(rs.getBoolean("enabled"));
                    user.setAccountNonExpired(rs.getBoolean("account_non_expired"));
                    user.setAccountNonLocked(rs.getBoolean("account_non_locked"));
                    user.setCredentialsNonExpired(rs.getBoolean("credentials_non_expired"));
                }, userName);

        List<AuthorityEntity> authorityEntities = jdbcTemplate.query("SELECT * FROM authorities WHERE user_id= ?",
                rs -> {
                    List<AuthorityEntity> authorities = new ArrayList<>();
                    while (rs.next()) {
                        AuthorityEntity authority = new AuthorityEntity();
                        authority.setAuthority(Authority.valueOf(rs.getString("authority")));
                    }
                    return authorities;
                },
                user.getId());
        user.setAuthorities(authorityEntities);
        return user;
    }

    @Override
    public String getUserIdByUsername(String userName) {
        return jdbcTemplate.queryForObject("SELECT id FROM users WHERE username = ?",
                String.class, userName);
    }
}
