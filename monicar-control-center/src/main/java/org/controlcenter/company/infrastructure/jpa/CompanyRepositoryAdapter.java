package org.controlcenter.company.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.company.application.port.CompanyRepository;
import org.controlcenter.company.domain.Company;
import org.controlcenter.company.infrastructure.jpa.entity.CompanyEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CompanyRepositoryAdapter implements CompanyRepository {
	private final CompanyJpaRepository companyJpaRepository;

	@Override
	public Company save(Company company) {
		return companyJpaRepository.save(CompanyEntity.from(company))
			.toDomain();
	}

	@Override
	public Optional<Company> findByCompanyName(String companyName) {
		return companyJpaRepository.findByCompanyName(companyName);
	}
}
