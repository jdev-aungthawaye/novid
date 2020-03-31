package jdev.novid.model.radar.infrastructure.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.persistence.jpa.JpaEntity;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.radar.domain.Location;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bis_location")
@Data
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class LocationEntity extends JpaEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void map(Location domain, LocationEntity state) {

        state.locationId = domain.getLocationId();
        state.userId = domain.getUserId();
        state.lat = domain.getLat();
        state.lng = domain.getLng();
        state.mac = domain.getMac();
        state.submittedAt = domain.getSubmittedAt();
        state.collectedAt = domain.getCollectedAt();

    }

    @EmbeddedId
    protected LocationId locationId;

    @Embedded
    protected UserId userId;

    @Column(name = "lat")
    protected BigDecimal lat;

    @Column(name = "lng")
    protected BigDecimal lng;

    @Column(name = "mac")
    @Convert(converter = MacAddress.JpaConverter.class)
    protected MacAddress mac;

    @Column(name = "submitted_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime submittedAt;

    @Column(name = "collected_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime collectedAt;

}
