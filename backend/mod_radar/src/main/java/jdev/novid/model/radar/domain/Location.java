package jdev.novid.model.radar.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.ddd.Snowflake;
import jdev.novid.model.domain.User;
import jdev.novid.model.radar.infrastructure.aerospike.LocationRecord;
import jdev.novid.model.radar.infrastructure.jpa.LocationEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {

    public static class Builder {

        protected static Location newInstance(User user, BigDecimal lat, BigDecimal lng, LocalDateTime collectedAt) {

            return new Location(user.getUserId(), lat, lng, collectedAt);

        }

        public static Location fromState(LocationEntity state) {

            Location domain = new Location();

            domain.locationId = state.getLocationId();
            domain.userId = state.getUserId();
            domain.lat = state.getLat();
            domain.lng = state.getLng();
            domain.mac = state.getMac();
            domain.submittedAt = state.getSubmittedAt();
            domain.collectedAt = state.getCollectedAt();

            return domain;

        }

        public static Location fromState(LocationRecord state) {

            Location domain = new Location();

            domain.locationId = state.getLocationId();
            domain.userId = state.getUserId();
            domain.lat = state.getLat();
            domain.lng = state.getLng();
            domain.mac = state.getMac();
            domain.submittedAt = state.getSubmittedAt();
            domain.collectedAt = state.getCollectedAt();

            return domain;

        }

    }

    protected LocationId locationId;

    protected UserId userId;

    protected BigDecimal lat;

    protected BigDecimal lng;

    protected MacAddress mac;

    protected LocalDateTime submittedAt;

    protected LocalDateTime collectedAt;

    public Location(UserId userId, BigDecimal lat, BigDecimal lng, LocalDateTime collectedAt) {

        super();

        this.locationId = new LocationId(Snowflake.get().nextId());
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.collectedAt = collectedAt;
        this.submittedAt = LocalDateTime.now();

    }

}
