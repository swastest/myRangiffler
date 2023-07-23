package org.rangiffler.jupiter.annotation;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.extension.BrowserConfigExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({AllureJunit5.class, BrowserConfigExtension.class})
public @interface WebTest {

}
