package org.controlcenter.geoinfo.infrastructure.jpa;

import org.controlcenter.geoinfo.infrastructure.jpa.entity.CycleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleInfoJpaRepository extends JpaRepository<CycleInfoEntity, Long> {
}
