package org.controlcenter.vehicle.infrastructure.jpa;

import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleInformationJpaRepository extends JpaRepository<VehicleInformationEntity, Long> {
}
