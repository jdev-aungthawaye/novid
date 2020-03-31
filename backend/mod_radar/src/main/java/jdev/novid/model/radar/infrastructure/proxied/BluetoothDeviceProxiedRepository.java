package jdev.novid.model.radar.infrastructure.proxied;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.radar.domain.BluetoothDevice;
import jdev.novid.model.radar.infrastructure.BluetoothDeviceRepository;

@Component
@Qualifier(PersistenceQualifiers.PROXIED)
public class BluetoothDeviceProxiedRepository implements BluetoothDeviceRepository {

    @Autowired
    @Qualifier(PersistenceQualifiers.JPA)
    private BluetoothDeviceRepository bluetoothDeviceJpaRepository;

    @Autowired
    @Qualifier(PersistenceQualifiers.AEROSPIKE)
    private BluetoothDeviceRepository bluetoothDeviceAerospikeRepository;

    @Override
    public void save(BluetoothDevice domain) {

        this.bluetoothDeviceJpaRepository.save(domain);
        this.bluetoothDeviceAerospikeRepository.save(domain);

    }

    @Override
    public void delete(LocationId id) {

        this.bluetoothDeviceJpaRepository.delete(id);
        this.bluetoothDeviceAerospikeRepository.delete(id);

    }

    @Override
    public BluetoothDevice get(LocationId id) {

        Optional<BluetoothDevice> optBluetoothDevice = this.bluetoothDeviceAerospikeRepository.findById(id);

        if (optBluetoothDevice.isPresent()) {

            return optBluetoothDevice.get();

        }

        BluetoothDevice bluetoothDevice = this.bluetoothDeviceJpaRepository.get(id);

        this.bluetoothDeviceAerospikeRepository.save(bluetoothDevice);

        return bluetoothDevice;

    }

    @Override
    public Optional<BluetoothDevice> findById(LocationId id) {

        Optional<BluetoothDevice> optBluetoothDevice = this.bluetoothDeviceAerospikeRepository.findById(id);

        if (optBluetoothDevice.isPresent()) {

            return optBluetoothDevice;

        }

        optBluetoothDevice = this.bluetoothDeviceJpaRepository.findById(id);

        if (optBluetoothDevice.isPresent()) {

            this.bluetoothDeviceAerospikeRepository.save(optBluetoothDevice.get());

        }

        return optBluetoothDevice;

    }

}
