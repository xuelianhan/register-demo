package com.github.register.infrastructure.configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;


import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Jersey Server Configuration
 * <p>
 * Using Jersey to provide support of JAX-RS(JSR 370：Java API for Restful Web Services)
 * The prefix path "restful" of all the services is set here.
 *
 * @author sniper
 * @date
 **/
@Configuration
@ApplicationPath("/restful")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        scanPackages("com.github.register.resource");
        scanPackages("com.github.register.infrastructure.jaxrs");
    }

    /**
     * Fix the bug while the method of packages() in Jersey is running with Jar style.
     */
    private void scanPackages(String scanPackage) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
        this.registerClasses(scanner.findCandidateComponents(scanPackage).stream()
                .map(beanDefinition -> ClassUtils.resolveClassName(Objects.requireNonNull(beanDefinition.getBeanClassName()), this.getClassLoader()))
                .collect(Collectors.toSet()));
    }


}
