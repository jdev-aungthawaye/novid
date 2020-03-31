package jdev.novid.model.radar.infrastructure.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
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
import jdev.novid.model.radar.domain.BluetoothDevice;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bis_device")
@Data
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class BluetoothDeviceEntity extends JpaEntity {

    public static void map(BluetoothDevice domain, BluetoothDeviceEntity state) {

        state.locationId = domain.getLocationId();
        state.sourceId = domain.getSourceId();
        state.selfDevice = domain.getSelfDevice();
        state.nearByDevice = domain.getNearByDevice();
        state.deviceName = domain.getDeviceName();
        state.lat = domain.getLat();
        state.lng = domain.getLng();
        state.submittedAt = domain.getSubmittedAt();
        state.collectedAt = domain.getCollectedAt();

    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected LocationId locationId;

    @Embedded
    @AttributeOverride(column = @Column(name = "source_id"), name = "id")
    protected UserId sourceId;

    @Column(name = "self_device")
    @Convert(converter = MacAddress.JpaConverter.class)
    protected MacAddress selfDevice;

    @Column(name = "near_by_device")
    @Convert(converter = MacAddress.JpaConverter.class)
    protected MacAddress nearByDevice;

    @Column(name = "device_name")
    protected String deviceName;

    @Column(name = "lat")
    protected BigDecimal lat;

    @Column(name = "lng")
    protected BigDecimal lng;

    @Column(name = "submitted_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime submittedAt;

    @Column(name = "collected_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime collectedAt;

}
