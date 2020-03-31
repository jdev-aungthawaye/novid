package jdev.novid.model.radar.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.ddd.Snowflake;
import jdev.novid.model.domain.User;
import jdev.novid.model.radar.infrastructure.aerospike.BluetoothDeviceRecord;
import jdev.novid.model.radar.infrastructure.jpa.BluetoothDeviceEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BluetoothDevice {

    public static class Builder {

        protected static BluetoothDevice newInstance(User user, MacAddress selfDevice, MacAddress nearByDevice,
                String deviceName, BigDecimal lat, BigDecimal lng, LocalDateTime collectedAt) {

            return new BluetoothDevice(user.getUserId(), selfDevice, nearByDevice, deviceName, lat, lng, collectedAt);

        }

        public static BluetoothDevice fromState(BluetoothDeviceEntity state) {

            BluetoothDevice domain = new BluetoothDevice();

            domain.locationId = state.getLocationId();
            domain.sourceId = state.getSourceId();
            domain.selfDevice = state.getSelfDevice();
            domain.nearByDevice = state.getNearByDevice();
            domain.deviceName = state.getDeviceName();
            domain.lat = state.getLat();
            domain.lng = state.getLng();
            domain.submittedAt = state.getSubmittedAt();
            domain.collectedAt = state.getCollectedAt();

            return domain;

        }

        public static BluetoothDevice fromState(BluetoothDeviceRecord state) {

            BluetoothDevice domain = new BluetoothDevice();

            domain.locationId = state.getLocationId();
            domain.sourceId = state.getSourceId();
            domain.selfDevice = state.getSelfDevice();
            domain.nearByDevice = state.getNearByDevice();
            domain.deviceName = state.getDeviceName();
            domain.lat = state.getLat();
            domain.lng = state.getLng();
            domain.submittedAt = state.getSubmittedAt();
            domain.collectedAt = state.getCollectedAt();

            return domain;

        }

    }

    protected LocationId locationId;

    protected UserId sourceId;

    protected MacAddress selfDevice;

    protected MacAddress nearByDevice;

    protected String deviceName;

    protected BigDecimal lat;

    protected BigDecimal lng;

    protected LocalDateTime submittedAt;

    protected LocalDateTime collectedAt;

    public BluetoothDevice(UserId sourceId, MacAddress selfDevice, MacAddress nearByDevice, String deviceName,
            BigDecimal lat, BigDecimal lng, LocalDateTime collectedAt) {

        super();

        this.locationId = new LocationId(Snowflake.get().nextId());
        this.sourceId = sourceId;
        this.selfDevice = selfDevice;
        this.nearByDevice = nearByDevice;
        this.deviceName = deviceName;
        this.lat = lat;
        this.lng = lng;
        this.submittedAt = LocalDateTime.now();
        this.collectedAt = collectedAt;

    }

}
