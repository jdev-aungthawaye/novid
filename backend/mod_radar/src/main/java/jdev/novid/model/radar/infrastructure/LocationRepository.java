package jdev.novid.model.radar.infrastructure;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.jpa.BasicRepository;
import jdev.novid.model.radar.domain.Location;

public interface LocationRepository extends BasicRepository<Location, LocationId> {

}
