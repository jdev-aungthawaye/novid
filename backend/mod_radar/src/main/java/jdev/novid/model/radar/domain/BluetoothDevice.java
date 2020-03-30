package jdev.novid.model.radar.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.ddd.Snowflake;
import jdev.novid.model.domain.User;

public class BluetoothDevice {

    public static class Builder {

        protected static BluetoothDevice newInstance(User user, MacAddress nearByDevice, String deviceName,
                BigDecimal lat, BigDecimal lng, LocalDateTime collectedAt) {

            return new BluetoothDevice(user.getUserId(), nearByDevice, deviceName, lat, lng, collectedAt);

        }

    }

    protected LocationId locationId;

    protected UserId sourceId;

    protected MacAddress nearByDevice;

    protected String deviceName;

    protected BigDecimal lat;

    protected BigDecimal lng;

    protected LocalDateTime submittedAt;

    protected LocalDateTime collectedAt;

    public BluetoothDevice(UserId sourceId, MacAddress nearByDevice, String deviceName, BigDecimal lat, BigDecimal lng,
            LocalDateTime collectedAt) {

        super();

        this.locationId = new LocationId(Snowflake.get().nextId());
        this.sourceId = sourceId;
        this.nearByDevice = nearByDevice;
        this.deviceName = deviceName;
        this.lat = lat;
        this.lng = lng;
        this.submittedAt = LocalDateTime.now();
        this.collectedAt = collectedAt;

    }

}
