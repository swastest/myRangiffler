package org.rangiffler.db.dao;

import org.rangiffler.db.entity.userdata.UserDataEntity;

public interface UserDataDao {
    int deleteUser(UserDataEntity userDataEntity);
    UserDataEntity userInfoByUserName(String username);
}
