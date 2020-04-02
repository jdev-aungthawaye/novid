package jdev.novid.model.radar.infrastructure.jpa;

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

    @Embedded
    @AttributeOverride(column = @Column(name = "near_by_user_id"), name = "id")
    protected MacAddress nearByUserId;

    @Column(name = "submitted_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime submittedAt;

    @Column(name = "collected_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime collectedAt;

}
