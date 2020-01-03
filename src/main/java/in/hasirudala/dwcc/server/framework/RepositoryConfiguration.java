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
        config.exposeIdsFor(Zone.class);
        config.exposeIdsFor(Dwcc.class);
        config.exposeIdsFor(WasteType.class);
        config.exposeIdsFor(WasteTag.class);
        config.exposeIdsFor(WasteItem.class);
        config.exposeIdsFor(VehicleType.class);
        config.exposeIdsFor(IncomingWasteRecord.class);
        config.exposeIdsFor(IncomingDtdWaste.class);
        config.exposeIdsFor(IncomingWasteItem.class);
        config.exposeIdsFor(IncomingMixedWaste.class);
        config.exposeIdsFor(WasteBuyer.class);
        config.exposeIdsFor(OutgoingWasteRecord.class);
        config.exposeIdsFor(OutgoingWasteItem.class);
    }
}
