package org.rangiffler.db.dao;

import org.rangiffler.db.entity.photo.PhotoEntity;
import org.rangiffler.db.managerDb.DataSourceProviderMySql;
import org.rangiffler.db.managerDb.ServiceDB;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class PhotoDaoJdbcImpl implements PhotoDao {
    private final JdbcTemplate jdbcTemplate;

    public PhotoDaoJdbcImpl() {
        this.jdbcTemplate = new JdbcTemplate(DataSourceProviderMySql.INSTANCE.getDataSource(ServiceDB.RANGIFFLER_PHOTO));
    }

    @Override
    public PhotoEntity findPhotoByUsername(String username) {
        String sql = "SELECT * FROM photos WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                PhotoEntity photo = new PhotoEntity();
                photo.convertSetId((byte[]) rs.getObject("id"));
                photo.setUsername(rs.getString("username"));
                photo.setCountryCode(rs.getString("country_code"));
                photo.setDescription(rs.getString("description"));
                photo.setPhoto(rs.getBytes("photo"));
                return photo;
            }, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteAllPhotoByUsername(String username) {
        jdbcTemplate.update("DELETE FROM photos where username =?", username);
    }
}
