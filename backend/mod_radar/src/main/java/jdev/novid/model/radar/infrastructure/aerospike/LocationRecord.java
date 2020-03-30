package jdev.novid.model.radar.infrastructure.aerospike;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.filosganga.geogson.model.Point;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.component.asmapper.annotation.AerospikeBin;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.radar.domain.Location;

@AerospikeRecord
public class LocationRecord implements Serializable {

    public static void map(Location domain, LocationRecord state) {

        state.userId = domain.getUserId();
        state.locationId = domain.getLocationId();
        state.lat = domain.getLat();
        state.lng = domain.getLng();
        state.submittedAt = domain.getSubmittedAt();
        state.collectedAt = domain.getCollectedAt();

        // Special case
        state.coordinate = Point.from(state.lng.doubleValue(), state.lat.doubleValue());

    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @AerospikeBin(name = "loc_id", converter = LocationId.AerospikeConverter.class)
    protected LocationId locationId;

    @AerospikeBin(name = "user_id", converter = UserId.AerospikeConverter.class)
    protected UserId userId;

    @AerospikeBin(name = "lat")
    protected BigDecimal lat;

    @AerospikeBin(name = "lng")
    protected BigDecimal lng;

    @AerospikeBin(name = "sub_at", converter = TimestampConverter.class)
    protected LocalDateTime submittedAt;

    @AerospikeBin(name = "col_at", converter = TimestampConverter.class)
    protected LocalDateTime collectedAt;

    protected Point coordinate;

}
