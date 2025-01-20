package org.eventhub.infrastructure.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleEventJpaRepository extends JpaRepository<VehicleEventEntity, Long> {

	@Query("SELECT v FROM vehicle_event v WHERE v.vehicleId = :vehicleId ORDER BY v.createdAt DESC LIMIT 1")
	Optional<VehicleEventEntity> findLatestById(@Param("vehicleId") long vehicleId);
}
