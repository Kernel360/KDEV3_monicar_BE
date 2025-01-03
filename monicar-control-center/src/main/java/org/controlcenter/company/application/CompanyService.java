package org.controlcenter.company.application;

import java.util.Optional;

import org.controlcenter.company.application.port.CompanyRepository;
import org.controlcenter.company.domain.Company;
import org.controlcenter.company.domain.CompanyCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CompanyService {
	private final CompanyRepository companyRepository;

	@Transactional
	public Company register(CompanyCreate command) {
		Company company = Company.create(command);

		return companyRepository.save(company);
	}

	private Optional<Company> findByCompanyName(String companyName) {
		return companyRepository.findByCompanyName(companyName);
	}
}
