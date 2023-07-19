package org.rangiffler.jupiter.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.extension.ApiLoginExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(ApiLoginExtension.class)
public @interface ApiLogin {
    String username() default "";
    String password() default "";

    GenerateUser[] user() default {};
}
