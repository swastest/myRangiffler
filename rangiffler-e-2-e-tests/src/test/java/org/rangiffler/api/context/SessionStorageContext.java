package org.rangiffler.api.context;

import java.util.HashMap;
import java.util.Map;

public class SessionStorageContext {
    private final Map<String, String> storage;
    private static final ThreadLocal<SessionStorageContext> INSTANCE =
            ThreadLocal.withInitial(() -> new SessionStorageContext());

    private static final String CODE_VERIFIER_KEY = "CODE_VERIFIER";
    private static final String CODE_CHALLANGE_KEY = "CODE_CHALLANGE";
    private static final String TOKEN_KEY = "TOKEN";
    private static final String CODE_KEY = "CODE";

    private SessionStorageContext() {
        storage = new HashMap<>();
    }

    public static SessionStorageContext getInstance() {
        return INSTANCE.get();
    }

    public String getCodeVerifier() {
        return storage.get(CODE_VERIFIER_KEY);
    }

    public String getCodeChallange() {
        return storage.get(CODE_CHALLANGE_KEY);
    }

    public String setToken(String token) {
        return storage.put(TOKEN_KEY, token);
    }

    public String getToken() {
        return storage.get(TOKEN_KEY);
    }

    public String setCodeVerifier(String codeVerifier) {
        return storage.put(CODE_VERIFIER_KEY, codeVerifier);
    }

    public String setCodeChallange(String codeChallange) {
        return storage.put(CODE_CHALLANGE_KEY, codeChallange);
    }

    public void setCode(String code){
        storage.put(CODE_KEY, code);
    }

    public String getCode(){
       return storage.get(CODE_KEY);
    }

    public void release() {
        storage.clear();
    }
}
