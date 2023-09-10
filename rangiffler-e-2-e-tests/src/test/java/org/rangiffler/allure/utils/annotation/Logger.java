package org.rangiffler.allure.utils.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.allure.utils.AllureLogAttachCallback;
import org.rangiffler.allure.utils.ErrorLoggerCallback;
import org.rangiffler.allure.utils.EventLoggerCallback;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({ErrorLoggerCallback.class, EventLoggerCallback.class, AllureLogAttachCallback.class})
public @interface Logger {
}
