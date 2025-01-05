package org.controlcenter.company.application.port;

import java.util.Optional;

import org.controlcenter.company.domain.Company;

public interface CompanyRepository {
	Company save(Company company);

	Optional<Company> findByCompanyName(String companyName);
}
