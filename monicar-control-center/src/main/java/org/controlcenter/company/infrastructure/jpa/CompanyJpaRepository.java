package org.controlcenter.company.infrastructure.jpa;

import org.controlcenter.company.domain.Company;
import org.controlcenter.company.infrastructure.jpa.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<Company> findByCompanyName(String companyName);
}
