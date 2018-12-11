package io.github.jhipster.registry.config.registry;


import io.github.jhipster.registry.config.Constants;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(Constants.CONFIGSERVER)
@Configuration
@EnableConfigServer
public class ConfigServerConfiguration {
}
