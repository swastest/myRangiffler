package org.rangiffler.utils;

public enum ErrorMessage {
    BAD_CREDENTIALS("Bad credentials"),
    PASSWORDS_SHOULD_BE_EQUAL("Passwords should be equal"),
    BAD_USERNAME("Allowed username length should be from 3 to 20 characters"),
    BAD_PASSWORD("Allowed password length should be from 3 to 12 characters");


    public final String content;

    ErrorMessage(String content) {
        this.content = content;
    }
}
