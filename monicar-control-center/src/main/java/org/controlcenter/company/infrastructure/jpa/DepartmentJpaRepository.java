package org.controlcenter.company.infrastructure.jpa;

import org.controlcenter.company.infrastructure.jpa.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepository extends JpaRepository<DepartmentEntity, Long> {
}
