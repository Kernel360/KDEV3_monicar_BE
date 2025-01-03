package org.controlcenter.history.infrastructure.jpa;

import org.controlcenter.history.infrastructure.jpa.entity.DrivingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingHistoryJpaRepository extends JpaRepository<DrivingHistoryEntity, Long> {
}
