package org.collector.infrastructure.repository;

import org.collector.domain.VehicleInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleInformationRepository extends JpaRepository<VehicleInformation, Long> {
	boolean existsByMdn(Long mdn);
}
