package org.controlcenter.alarm.infrastructure.jpa;

import org.controlcenter.alarm.infrastructure.jpa.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {
}
