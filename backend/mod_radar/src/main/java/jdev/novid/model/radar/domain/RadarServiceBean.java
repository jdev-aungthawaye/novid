package jdev.novid.model.radar.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.domain.User;
import jdev.novid.model.radar.infrastructure.BluetoothDeviceRepository;
import jdev.novid.model.radar.infrastructure.LocationRepository;

@Service
public class RadarServiceBean implements RadarService {

    @Autowired
    @Qualifier(PersistenceQualifiers.PROXIED)
    private LocationRepository locationRepository;

    @Autowired
    @Qualifier(PersistenceQualifiers.PROXIED)
    private BluetoothDeviceRepository bluetoothDeviceRepository;

    @Override
    @Transactional
    public Location addLocation(User user, BigDecimal lat, BigDecimal lng, LocalDateTime collectedAt) {

        Location location = Location.Builder.newInstance(user, lat, lng, collectedAt);

        this.locationRepository.save(location);

        return location;

    }

    @Override
    @Transactional
    public BluetoothDevice addDevice(User user, User nearByUserId, LocalDateTime collectedAt) {

        BluetoothDevice device = BluetoothDevice.Builder.newInstance(user, nearByUserId, collectedAt);

        this.bluetoothDeviceRepository.save(device);

        return device;

    }

}
