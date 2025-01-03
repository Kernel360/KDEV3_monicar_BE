package org.controlcenter.company.infrastructure.jpa;

import org.controlcenter.company.infrastructure.jpa.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {
}
