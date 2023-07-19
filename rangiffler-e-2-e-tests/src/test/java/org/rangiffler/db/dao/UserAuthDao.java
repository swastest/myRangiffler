package org.rangiffler.db.dao;

import org.rangiffler.db.entity.auth.UserAuthEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserAuthDao {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    int createUser(UserAuthEntity user);
    int updateUser(UserAuthEntity user);
    void deleteUser(UserAuthEntity user);
    UserAuthEntity userInfo(String userName);
    String getUserId(String userName);
}
