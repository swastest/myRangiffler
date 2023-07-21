package org.rangiffler.jupiter.annotation;

import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.extension.GenerateUserExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(GenerateUserExtension.class)
public @interface GenerateUser {
    String username() default "";
    String password() default "";

    Friend[] friends() default {};

    Friend[] outcomeInvitations() default {};

    Friend[] incomeInvitations() default {};

//    Category[] categories() default {};
//
//    GenerateSpend[] spends() default {};
}
