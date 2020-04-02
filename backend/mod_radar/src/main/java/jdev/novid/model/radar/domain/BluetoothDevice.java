package jdev.novid.model.radar.domain;

import java.time.LocalDateTime;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
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

        protected static BluetoothDevice newInstance(User user, User nearByUser, LocalDateTime collectedAt) {

            return new BluetoothDevice(user.getUserId(), nearByUser.getUserId(), collectedAt);

        }

        public static BluetoothDevice fromState(BluetoothDeviceEntity state) {

            BluetoothDevice domain = new BluetoothDevice();

            domain.locationId = state.getLocationId();
            domain.sourceId = state.getSourceId();
            domain.submittedAt = state.getSubmittedAt();
            domain.collectedAt = state.getCollectedAt();

            return domain;

        }

        public static BluetoothDevice fromState(BluetoothDeviceRecord state) {

            BluetoothDevice domain = new BluetoothDevice();

            domain.locationId = state.getLocationId();
            domain.sourceId = state.getSourceId();
            domain.submittedAt = state.getSubmittedAt();
            domain.collectedAt = state.getCollectedAt();

            return domain;

        }

    }

    protected LocationId locationId;

    protected UserId sourceId;

    protected UserId nearByUserId;

    protected LocalDateTime submittedAt;

    protected LocalDateTime collectedAt;

    public BluetoothDevice(UserId sourceId, UserId nearByUserId, LocalDateTime collectedAt) {

        super();

        this.locationId = new LocationId(Snowflake.get().nextId());
        this.sourceId = sourceId;
        this.nearByUserId = nearByUserId;
        this.submittedAt = LocalDateTime.now();
        this.collectedAt = collectedAt;

    }

}
