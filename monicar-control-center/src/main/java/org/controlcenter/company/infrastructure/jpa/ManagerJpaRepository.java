package org.controlcenter.company.infrastructure.jpa;

import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerJpaRepository extends JpaRepository<ManagerEntity, Long> {
}
