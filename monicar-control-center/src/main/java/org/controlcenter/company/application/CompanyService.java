package org.controlcenter.company.application;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
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
		validateAlreadyExistCompanyName(command.getCompanyName());

		return companyRepository.save(Company.create(command));
	}

	private void validateAlreadyExistCompanyName(String companyName) {
		companyRepository.findByCompanyName(companyName)
			.ifPresent(existCompany -> {
				throw new BusinessException(ErrorCode.ENTITY_ALREADY_EXIST);
			});
	}

}
