package jdev.novid.model.radar.infrastructure.aerospike;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.filosganga.geogson.model.Point;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.asmapper.annotation.AerospikeBin;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.radar.domain.BluetoothDevice;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AerospikeRecord
@Data
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class BluetoothDeviceRecord {

    public static void map(BluetoothDevice domain, BluetoothDeviceRecord state) {

        state.locationId = domain.getLocationId();
        state.sourceId = domain.getSourceId();
        state.selfDevice = domain.getSelfDevice();
        state.nearByDevice = domain.getNearByDevice();
        state.deviceName = domain.getDeviceName();
        state.lat = domain.getLat();
        state.lng = domain.getLng();
        state.submittedAt = domain.getSubmittedAt();
        state.collectedAt = domain.getCollectedAt();

        // Special case
        state.coordinate = Point.from(state.lng.doubleValue(), state.lat.doubleValue());

    }

    @AerospikeBin(name = "loc_id", converter = LocationId.AerospikeConverter.class)
    protected LocationId locationId;

    @AerospikeBin(name = "src_id", converter = UserId.AerospikeConverter.class)
    protected UserId sourceId;

    @AerospikeBin(name = "sd_mac", converter = MacAddress.AerospikeConverter.class)
    protected MacAddress selfDevice;

    @AerospikeBin(name = "nbd_mac", converter = MacAddress.AerospikeConverter.class)
    protected MacAddress nearByDevice;

    @AerospikeBin(name = "d_name")
    protected String deviceName;

    @AerospikeBin(name = "lat")
    protected BigDecimal lat;

    @AerospikeBin(name = "lng")
    protected BigDecimal lng;

    @AerospikeBin(name = "sub_at", converter = TimestampConverter.class)
    protected LocalDateTime submittedAt;

    @AerospikeBin(name = "col_at", converter = TimestampConverter.class)
    protected LocalDateTime collectedAt;

    @AerospikeBin(name = "coord")
    protected Point coordinate;

}
