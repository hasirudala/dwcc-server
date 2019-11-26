package in.hasirudala.dwcc.server.framework;

import in.hasirudala.dwcc.server.domain.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;


@Configuration
public class RepositoryConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Region.class);
        config.exposeIdsFor(Ward.class);
    }
}