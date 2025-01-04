package org.collector.infrastructure.repository;

import java.util.Optional;

import org.collector.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	Optional<Vehicle> findByMdn(Long mdn);
}
