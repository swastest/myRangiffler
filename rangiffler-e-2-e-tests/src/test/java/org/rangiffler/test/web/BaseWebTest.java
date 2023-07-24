package org.rangiffler.test.web;

import org.rangiffler.jupiter.annotation.WebTest;
import org.rangiffler.page.LandingPage;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.RegisterPage;
import org.rangiffler.page.TravelsPage;
import org.rangiffler.page.component.MainNavigationTabs;
import org.rangiffler.page.component.UserHeader;

@WebTest
public abstract class BaseWebTest {
   LandingPage landingPage = new LandingPage();
   LoginPage loginPage = new LoginPage();
   RegisterPage registerPage = new RegisterPage();
   UserHeader header = new UserHeader();
   MainNavigationTabs tabs = new MainNavigationTabs();
   TravelsPage yourTravelsPage = new TravelsPage();
}
