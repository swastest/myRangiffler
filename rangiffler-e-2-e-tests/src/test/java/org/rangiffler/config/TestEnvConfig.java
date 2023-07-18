package org.rangiffler.config;

import org.aeonbits.owner.Config;
@Config.Sources("classpath:config/${test.env}.properties")
public interface TestEnvConfig extends Config {

}
