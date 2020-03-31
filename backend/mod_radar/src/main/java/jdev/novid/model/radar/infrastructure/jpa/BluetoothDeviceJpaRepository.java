package jdev.novid.model.radar.infrastructure.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.radar.domain.BluetoothDevice;
import jdev.novid.model.radar.infrastructure.BluetoothDeviceRepository;

@Component
@Qualifier(PersistenceQualifiers.JPA)
public class BluetoothDeviceJpaRepository implements BluetoothDeviceRepository {

    @Autowired
    private BluetoothDeviceEntityRepository bluetoothDeviceEntityRepository;

    @Override
    public void save(BluetoothDevice domain) {

        Optional<BluetoothDeviceEntity> optEntity = this.bluetoothDeviceEntityRepository
                .findById(domain.getLocationId());

        BluetoothDeviceEntity entity = optEntity.isPresent() ? optEntity.get() : new BluetoothDeviceEntity();

        BluetoothDeviceEntity.map(domain, entity);

        this.bluetoothDeviceEntityRepository.saveAndFlush(entity);

    }

    @Override
    public void delete(LocationId id) {

        this.bluetoothDeviceEntityRepository.deleteById(id);

    }

    @Override
    public BluetoothDevice get(LocationId id) {

        return BluetoothDevice.Builder.fromState(this.bluetoothDeviceEntityRepository.getOne(id));

    }

    @Override
    public Optional<BluetoothDevice> findById(LocationId id) {

        Optional<BluetoothDeviceEntity> optBluetoothDeviceEntity = this.bluetoothDeviceEntityRepository.findById(id);

        if (optBluetoothDeviceEntity.isPresent()) {

            return Optional.of(BluetoothDevice.Builder.fromState(optBluetoothDeviceEntity.get()));

        }

        return Optional.empty();

    }

}
