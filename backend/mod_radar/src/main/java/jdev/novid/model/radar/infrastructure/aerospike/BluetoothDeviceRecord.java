package jdev.novid.model.radar.infrastructure.aerospike;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.filosganga.geogson.model.Point;

import jdev.novid.common.identity.LocationId;
import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;
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

}
