package org.collector.infrastructure.repository;

import java.util.Optional;

import org.collector.domain.VehicleInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleInformation, Long> {
	Optional<VehicleInformation> findByMdn(Long mdn);
}
