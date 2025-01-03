package org.controlcenter.vehicle.infrastructure.jpa;

import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleEventJpaRepository extends JpaRepository<VehicleEventEntity, Long> {
}
