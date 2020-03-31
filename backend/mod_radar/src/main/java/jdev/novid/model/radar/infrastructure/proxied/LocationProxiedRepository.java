package jdev.novid.model.radar.infrastructure.proxied;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.radar.domain.Location;
import jdev.novid.model.radar.infrastructure.LocationRepository;

@Component
@Qualifier(PersistenceQualifiers.PROXIED)
public class LocationProxiedRepository implements LocationRepository {

    @Autowired
    @Qualifier(PersistenceQualifiers.JPA)
    private LocationRepository locationJpaRepository;

    @Autowired
    @Qualifier(PersistenceQualifiers.AEROSPIKE)
    private LocationRepository locationAerospikeRepository;

    @Override
    public void save(Location domain) {

        this.locationJpaRepository.save(domain);
        this.locationAerospikeRepository.save(domain);

    }

    @Override
    public void delete(LocationId id) {

        this.locationJpaRepository.delete(id);
        this.locationAerospikeRepository.delete(id);

    }

    @Override
    public Location get(LocationId id) {

        Optional<Location> optLocation = this.locationAerospikeRepository.findById(id);

        if (optLocation.isPresent()) {

            return optLocation.get();

        }

        Location location = this.locationJpaRepository.get(id);

        this.locationAerospikeRepository.save(location);

        return location;

    }

    @Override
    public Optional<Location> findById(LocationId id) {

        Optional<Location> optLocation = this.locationAerospikeRepository.findById(id);

        if (optLocation.isPresent()) {

            return optLocation;

        }

        optLocation = this.locationJpaRepository.findById(id);

        if (optLocation.isPresent()) {

            this.locationAerospikeRepository.save(optLocation.get());

        }

        return optLocation;

    }

}
