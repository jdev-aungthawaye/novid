package jdev.novid.model.radar.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jdev.novid.common.identity.LocationId;

@Repository
public interface BluetoothDeviceEntityRepository extends JpaRepository<BluetoothDeviceEntity, LocationId>{

}
