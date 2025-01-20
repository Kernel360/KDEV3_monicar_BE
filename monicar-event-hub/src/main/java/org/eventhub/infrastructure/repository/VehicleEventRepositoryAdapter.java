package org.eventhub.infrastructure.repository;

import java.util.Optional;

import org.eventhub.application.port.VehicleEventRepository;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.infrastructure.repository.jpa.VehicleEventEntity;
import org.eventhub.infrastructure.repository.jpa.VehicleEventJpaRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleEventRepositoryAdapter implements VehicleEventRepository {
	private final VehicleEventJpaRepository vehicleEventJpaRepository;

	@Override
	public VehicleEvent save(VehicleEvent vehicleEvent) {
		return vehicleEventJpaRepository.save(VehicleEventEntity.from(vehicleEvent)).toDomain();
	}

	@Override
	public Optional<VehicleEvent> findLatestById(long vehicleId) {
		return vehicleEventJpaRepository.findLatestById(vehicleId)
			.map(VehicleEventEntity::toDomain);
	}
}
