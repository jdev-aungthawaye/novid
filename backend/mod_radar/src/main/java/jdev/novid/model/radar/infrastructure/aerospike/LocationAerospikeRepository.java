package jdev.novid.model.radar.infrastructure.aerospike;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.LocationId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.radar.domain.Location;
import jdev.novid.model.radar.infrastructure.LocationRepository;

@Component
@Qualifier(PersistenceQualifiers.AEROSPIKE)
public class LocationAerospikeRepository implements LocationRepository {

    @Autowired
    private LocationRecordRepository locationRecordRepository;

    @Override
    public void save(Location domain) {

        LocationRecord state = new LocationRecord();

        LocationRecord.map(domain, state);

        this.locationRecordRepository.save(state);

    }

    @Override
    public void delete(LocationId id) {

        this.locationRecordRepository.delete(id);

    }

    @Override
    public Location get(LocationId id) {

        return Location.Builder.fromState(this.locationRecordRepository.get(id));

    }

    @Override
    public Optional<Location> findById(LocationId id) {

        Optional<LocationRecord> optLocationRecord = this.locationRecordRepository.findById(id);

        if (optLocationRecord.isPresent()) {

            return Optional.of(Location.Builder.fromState(optLocationRecord.get()));

        }

        return Optional.empty();

    }

}
