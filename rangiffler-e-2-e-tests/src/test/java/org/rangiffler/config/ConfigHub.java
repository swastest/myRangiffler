package org.rangiffler.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHub {
    public static TestEnvConfig configEnv = ConfigFactory.create(TestEnvConfig.class, System.getProperties());
    public static BrowserConfig configBrowser = ConfigFactory.create(BrowserConfig.class, System.getProperties());
}
