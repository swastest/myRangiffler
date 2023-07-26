package org.rangiffler.db.dao;

import org.rangiffler.db.entity.photo.PhotoEntity;

public interface PhotoDao {
    PhotoEntity findPhotoByUsername(String username);
    void deleteAllPhotoByUsername(String username);
}
