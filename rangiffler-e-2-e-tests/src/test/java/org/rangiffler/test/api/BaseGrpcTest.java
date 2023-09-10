package org.rangiffler.test.api;

import org.rangiffler.allure.utils.annotation.Logger;
import org.rangiffler.api.grpc.CountryGrpcClient;
import org.rangiffler.api.grpc.PhotoGrpcClient;
import org.rangiffler.db.dao.PhotoDao;
import org.rangiffler.db.dao.PhotoDaoJdbcImpl;
import org.rangiffler.jupiter.annotation.GrpcTest;
@Logger
@GrpcTest
public abstract class BaseGrpcTest {
    protected CountryGrpcClient countryGrpcClient = new CountryGrpcClient();
    protected PhotoGrpcClient photoGrpcClient = new PhotoGrpcClient();

    protected PhotoDao photoDao = new PhotoDaoJdbcImpl();

}
