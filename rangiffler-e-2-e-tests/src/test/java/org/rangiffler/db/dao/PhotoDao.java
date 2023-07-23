package org.rangiffler.db.dao;

import org.rangiffler.db.entity.photo.PhotoEntity;

import java.util.List;

public interface PhotoDao {
    List<PhotoEntity> findAllPhotoByUsername(String username);
    void deleteAllPhotoByUsername(String username);
}
