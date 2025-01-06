package org.controlcenter.cycleinfo.infrastructure.jpa;

import org.controlcenter.cycleinfo.infrastructure.jpa.entity.CycleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleInfoJpaRepository extends JpaRepository<CycleInfoEntity, Long> {
}
