package org.rangiffler.jupiter.extension;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.rangiffler.config.ConfigHub;

import java.io.ByteArrayInputStream;

public class BrowserConfigExtension implements SuiteExtension, AfterEachCallback, TestExecutionExceptionHandler {

    @Override
    public void beforeSuite() {
        Configuration.browser = ConfigHub.configBrowser.browserName();
        Configuration.browserVersion = ConfigHub.configBrowser.browserVersion();
        Configuration.browserSize = ConfigHub.configBrowser.browserSize();
        if("docker".equals(System.getProperty("test.env"))) {
            Configuration.remote = ConfigHub.configBrowser.browserRemoteUrl();
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Selenide.closeWebDriver();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Allure.addAttachment("Screen on fail",
                    new ByteArrayInputStream(((TakesScreenshot) WebDriverRunner.getWebDriver())
                            .getScreenshotAs(OutputType.BYTES))
            );
        }

        throw throwable;
    }

}
