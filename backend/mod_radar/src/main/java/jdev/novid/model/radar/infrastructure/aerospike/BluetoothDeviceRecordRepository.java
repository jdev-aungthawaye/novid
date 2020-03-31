package jdev.novid.model.radar.infrastructure.aerospike;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.query.IndexType;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.asmapper.AerospikeTemplate;
import jdev.novid.component.persistence.jpa.BasicRepository;

@Component
public class BluetoothDeviceRecordRepository implements BasicRepository<BluetoothDeviceRecord, LocationId> {

    private static final String SET = "device";

    @Autowired
    private AerospikeTemplate aerospikeTemplate;

    public void save(BluetoothDeviceRecord record) {

        this.aerospikeTemplate.save(record.getLocationId().getId(), SET, record);

    }

    @Override
    public void delete(LocationId id) {

        this.aerospikeTemplate.delete(id.getId(), SET);

    }

    @Override
    public BluetoothDeviceRecord get(LocationId id) {

        Optional<BluetoothDeviceRecord> optional = this.aerospikeTemplate.find(id.getId(), SET,
                BluetoothDeviceRecord.class);

        if (optional.isPresent()) {

            return optional.get();

        }

        throw new EntityNotFoundException("BluetoothDeviceRecord with PK [" + id + "] not found in Aerospike.");

    }

    @PostConstruct
    private void postConstruct() {

        this.aerospikeTemplate.createIndex("idx_device_coord", SET, "coord", IndexType.GEO2DSPHERE);

    }

    @Override
    public Optional<BluetoothDeviceRecord> findById(LocationId id) {

        return this.aerospikeTemplate.find(id.getId(), SET, BluetoothDeviceRecord.class);

    }

}
