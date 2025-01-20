package org.eventhub.infrastructure.repository;

import java.util.Optional;

import org.eventhub.application.port.VehicleRepository;
import org.eventhub.domain.VehicleInformation;
import org.eventhub.infrastructure.repository.jpa.VehicleInformationEntity;
import org.eventhub.infrastructure.repository.jpa.VehicleInformationJpaRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleInformationRepositoryAdapter implements VehicleRepository {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;

	@Override
	public Optional<VehicleInformation> findById(Long vehicleId) {
		return vehicleInformationJpaRepository.findById(vehicleId)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public Optional<VehicleInformation> findByMdn(Long mdn) {
		return vehicleInformationJpaRepository.findByMdn(mdn)
			.map(VehicleInformationEntity::toDomain);
	}

}
