package org.rangiffler.api.context;

import java.util.HashMap;
import java.util.Map;

public class CookieContext {
    private final Map<String, String> storage;
    private static final ThreadLocal<CookieContext> INSTANCE =
            ThreadLocal.withInitial(() -> new CookieContext());


    private CookieContext() {
        storage = new HashMap<>();
    }

    public static CookieContext getInstance() {
        return INSTANCE.get();
    }

    public String setCookie(String keyNameCookie, String cookie) {
        return storage.put(keyNameCookie, cookie);
    }

    public String getCookie(String keyNameCookie) {
        return storage.get(keyNameCookie);
    }
    public String deleteCookie(String keyNameCookie) {
        return storage.remove(keyNameCookie);
    }

    public String getFormattedCookie(String keyNameCookie) {
        return keyNameCookie + "=" + getCookie(keyNameCookie);
    }

    public void release() {
        storage.clear();
    }
}
