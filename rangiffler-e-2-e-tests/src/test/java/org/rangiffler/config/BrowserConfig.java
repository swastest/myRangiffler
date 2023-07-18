package org.rangiffler.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/${browser}.properties")
public interface BrowserConfig extends Config {
    @Key("browser.name")
    String browserName();
    @Key("browser.version")
    String browserVersion();
    @Key("browser.size")
    String browserSize();
    @Key("browser.remote.url")
    String browserRemoteUrl();
}
