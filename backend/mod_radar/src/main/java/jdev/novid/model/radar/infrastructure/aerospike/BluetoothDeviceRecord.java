package jdev.novid.model.radar.infrastructure.aerospike;

import java.time.LocalDateTime;

import com.github.filosganga.geogson.model.Point;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
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
        state.submittedAt = domain.getSubmittedAt();
        state.collectedAt = domain.getCollectedAt();

    }

    @AerospikeBin(name = "loc_id", converter = LocationId.AerospikeConverter.class)
    protected LocationId locationId;

    @AerospikeBin(name = "src_id", converter = UserId.AerospikeConverter.class)
    protected UserId sourceId;

    @AerospikeBin(name = "src_id", converter = UserId.AerospikeConverter.class)
    protected UserId nearByUserId;

    @AerospikeBin(name = "sub_at", converter = TimestampConverter.class)
    protected LocalDateTime submittedAt;

    @AerospikeBin(name = "col_at", converter = TimestampConverter.class)
    protected LocalDateTime collectedAt;

    @AerospikeBin(name = "coord")
    protected Point coordinate;

}
