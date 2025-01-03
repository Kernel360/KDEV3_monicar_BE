package org.controlcenter.vehicle.infrastructure.jpa;

import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypesJpaRepository extends JpaRepository<VehicleTypeEntity, Long> {
}
