package io.github.jhipster.registry.config.apidoc;

import java.util.ArrayList;
import java.util.List;

import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * Retrieves all registered microservices Swagger resources.
 */
@Component
@Primary
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

    private final Logger log = LoggerFactory.getLogger(GatewaySwaggerResourcesProvider.class);

    private final RouteLocator routeLocator;

    private final DiscoveryClient discoveryClient;

    public GatewaySwaggerResourcesProvider(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        //Add the registry's swagger API and management resource
        resources.add(swaggerResource("JHipster Registry API", "/v2/api-docs"));
        resources.add(swaggerResource("JHipster Registry management", "/v2/api-docs?group=management"));

        //Add the registered microservices management swagger docs as additional swagger resources
        List<Route> routes = routeLocator.getRoutes();
        routes.forEach(route -> {
            resources.add(swaggerResource(route.getId() + " management", route.getFullPath().replace("**", "v2/api-docs?group=management")));
        });

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
