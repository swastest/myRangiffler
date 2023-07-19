package org.rangiffler.db.dao;

import org.rangiffler.db.entity.auth.UserAuthEntity;
import org.rangiffler.db.jpa.EntityManagerFactoryProviderMySql;
import org.rangiffler.db.jpa.JpaTransactionManager;
import org.rangiffler.db.managerDb.ServiceDB;

public class UserAuthDaoImpl extends JpaTransactionManager implements UserAuthDao {
    public UserAuthDaoImpl() {
        super(EntityManagerFactoryProviderMySql.INSTANCE.getEmf(ServiceDB.RANGIFFLER_AUTH).createEntityManager());
    }

    @Override
    public int createUser(UserAuthEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        persist(user);
        return 0;
    }

    @Override
    public int updateUser(UserAuthEntity user) {
        merge(user);
        return 0;
    }

    @Override
    public void deleteUser(UserAuthEntity user) {
        transaction(em ->  em.remove(user));
    }

    @Override
    public UserAuthEntity userInfo(String userName) {
        return em.createQuery("SELECT u FROM UserAuthEntity u WHERE username = :username", UserAuthEntity.class)
                .setParameter("username", userName)
                .getSingleResult();
    }

    @Override
    public String getUserId(String userName) {
        return em.createQuery("SELECT u FROM UserAuthEntity u WHERE username = :username", UserAuthEntity.class)
                .setParameter("username", userName)
                .getSingleResult()
                .getId()
                .toString();
    }
}
