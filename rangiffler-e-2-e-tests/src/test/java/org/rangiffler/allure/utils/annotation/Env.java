package org.rangiffler.allure.utils.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.allure.utils.EnvironmentExecutionCondition;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@ExtendWith(EnvironmentExecutionCondition.class)
public @interface Env {

    String[] enabledFor() default {""};
}
