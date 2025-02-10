package org.eventhub.application.port;

import java.util.Optional;

import org.eventhub.domain.VehicleInformation;
import org.eventhub.domain.UpdateTotalDistance;
import org.eventhub.domain.VehicleStatus;

public interface VehicleRepository {
	Optional<VehicleInformation> findById(Long vehicleId);
	Optional<VehicleInformation> findByMdn(Long mdn);
	Long updateTotalDistance(UpdateTotalDistance updateTotalDistanceDto);

	VehicleStatus updateVehicleStatus(Long vehicleId, VehicleStatus vehicleStatus);

	void updateDrivingDaysAll();
}
