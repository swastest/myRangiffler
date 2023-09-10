package org.rangiffler.test.web;

import guru.qa.grpc.rangiffler.grpc.CountryByCodeRequest;
import org.rangiffler.allure.utils.annotation.Logger;
import org.rangiffler.jupiter.annotation.WebTest;
import org.rangiffler.page.LandingPage;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.RegisterPage;
import org.rangiffler.page.TravelsPage;
import org.rangiffler.page.component.MainNavigationTabs;
import org.rangiffler.page.component.UserHeader;

@Logger
@WebTest
public abstract class BaseWebTest {
    protected LandingPage landingPage = new LandingPage();
    protected LoginPage loginPage = new LoginPage();
    protected RegisterPage registerPage = new RegisterPage();
    protected UserHeader header = new UserHeader();
    protected MainNavigationTabs tabs = new MainNavigationTabs();
    protected TravelsPage yourTravelsPage = new TravelsPage();
}
