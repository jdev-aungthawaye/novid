package jdev.novid.model.radar.infrastructure.aerospike;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.radar.domain.BluetoothDevice;
import jdev.novid.model.radar.infrastructure.BluetoothDeviceRepository;

@Component
@Qualifier(PersistenceQualifiers.AEROSPIKE)
public class BluetoothDeviceAerospikeRepository implements BluetoothDeviceRepository {

    @Autowired
    private BluetoothDeviceRecordRepository bluetoothDeviceRecordRepository;

    @Override
    public void save(BluetoothDevice domain) {

        BluetoothDeviceRecord state = new BluetoothDeviceRecord();

        BluetoothDeviceRecord.map(domain, state);

        this.bluetoothDeviceRecordRepository.save(state);

    }

    @Override
    public void delete(LocationId id) {

        this.bluetoothDeviceRecordRepository.delete(id);

    }

    @Override
    public BluetoothDevice get(LocationId id) {

        return BluetoothDevice.Builder.fromState(this.bluetoothDeviceRecordRepository.get(id));

    }

    @Override
    public Optional<BluetoothDevice> findById(LocationId id) {

        Optional<BluetoothDeviceRecord> optBluetoothDeviceRecord = this.bluetoothDeviceRecordRepository.findById(id);

        if (optBluetoothDeviceRecord.isPresent()) {

            return Optional.of(BluetoothDevice.Builder.fromState(optBluetoothDeviceRecord.get()));

        }

        return Optional.empty();

    }

}
