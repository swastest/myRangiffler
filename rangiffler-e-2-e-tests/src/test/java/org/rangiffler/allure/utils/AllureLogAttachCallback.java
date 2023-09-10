package org.rangiffler.allure.utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rangiffler.allure.addLogs.AllureLogAppender;

public class AllureLogAttachCallback implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        String log = AllureLogAppender.getInstance().getLog();
        AllureLogAppender.getInstance().clearLog();
        Allure.addAttachment("Logs", "text/html", log, "html");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        AllureLogAppender.getInstance().clearLog();
    }

}