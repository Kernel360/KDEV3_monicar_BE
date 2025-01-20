package org.eventhub.application.port;

import java.util.Optional;

import org.eventhub.domain.VehicleInformation;

public interface VehicleRepository {
	Optional<VehicleInformation> findById(Long vehicleId);
	Optional<VehicleInformation> findByMdn(Long mdn);

}
