package org.rangiffler.db.dao;

import org.rangiffler.db.entity.photo.PhotoEntity;
import org.rangiffler.db.managerDb.DataSourceProviderMySql;
import org.rangiffler.db.managerDb.ServiceDB;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class PhotoDaoJdbcImpl implements PhotoDao {
    private final JdbcTemplate jdbcTemplate;

    public PhotoDaoJdbcImpl() {
        this.jdbcTemplate = new JdbcTemplate(DataSourceProviderMySql.INSTANCE.getDataSource(ServiceDB.RANGIFFLER_PHOTO));
    }

    @Override
    public List<PhotoEntity> findAllPhotoByUsername(String username) {
        List<PhotoEntity> photos = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM photos WHERE username =?", rs -> {
            while (rs.next()) {
                PhotoEntity photo = new PhotoEntity();
                photo.convertSetId((byte[]) rs.getObject("id"));
                photo.setUsername(rs.getString("username"));
                photo.setCountryCode(rs.getString("country_code"));
                photo.setDescription(rs.getString("description"));
                photo.setPhoto(rs.getBytes("photo"));
                photos.add(photo);
            }
        }, username);
        return photos;
    }

    @Override
    public void deleteAllPhotoByUsername(String username) {
        jdbcTemplate.update("DELETE FROM photos where username =?", username);
    }
}
