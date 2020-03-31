package jdev.novid.model.radar.infrastructure.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.radar.domain.Location;
import jdev.novid.model.radar.infrastructure.LocationRepository;

@Component
@Qualifier(PersistenceQualifiers.JPA)
public class LocationJpaRepository implements LocationRepository {

    @Autowired
    private LocationEntityRepository locationEntityRepository;

    @Override
    public void save(Location domain) {

        Optional<LocationEntity> optEntity = this.locationEntityRepository.findById(domain.getLocationId());

        LocationEntity entity = optEntity.isPresent() ? optEntity.get() : new LocationEntity();

        LocationEntity.map(domain, entity);

        this.locationEntityRepository.saveAndFlush(entity);

    }

    @Override
    public void delete(LocationId id) {

        this.locationEntityRepository.deleteById(id);

    }

    @Override
    public Location get(LocationId id) {

        return Location.Builder.fromState(this.locationEntityRepository.getOne(id));

    }

    @Override
    public Optional<Location> findById(LocationId id) {

        Optional<LocationEntity> optLocationEntity = this.locationEntityRepository.findById(id);

        if (optLocationEntity.isPresent()) {

            return Optional.of(Location.Builder.fromState(optLocationEntity.get()));

        }

        return Optional.empty();

    }
}
