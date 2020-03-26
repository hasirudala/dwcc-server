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
        config.exposeIdsFor(IncomingDtdWasteEntry.class);
        config.exposeIdsFor(IncomingMixedWasteEntry.class);
        config.exposeIdsFor(WasteBuyer.class);
        config.exposeIdsFor(OutgoingWasteRecord.class);
        config.exposeIdsFor(OutgoingWasteEntry.class);
        config.exposeIdsFor(ExpenseType.class);
        config.exposeIdsFor(ExpenseTag.class);
        config.exposeIdsFor(ExpenseItem.class);
        config.exposeIdsFor(ExpenseRecord.class);
        config.exposeIdsFor(ExpenseEntry.class);
        config.exposeIdsFor(ExpensePurchaseEntry.class);
    }
}
