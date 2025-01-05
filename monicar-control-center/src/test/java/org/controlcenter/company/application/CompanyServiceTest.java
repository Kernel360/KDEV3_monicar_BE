package org.controlcenter.company.application;

import static org.assertj.core.api.Assertions.*;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.domain.Company;
import org.controlcenter.company.domain.CompanyCreate;
import org.controlcenter.company.infrastructure.jpa.CompanyJpaRepository;
import org.controlcenter.company.infrastructure.jpa.entity.CompanyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("[service 통합테스트] CompanyService")
@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyJpaRepository companyJpaRepository;

	@DisplayName("이미 등록된 회사이름이 존재한다면, 새로 등록할 수 없다.")
	@Test
	void registerWithAlreadyExistsCompanyName() {
		// given
		Company company = Company.builder()
			.companyName("company")
			.businessRegistrationNumber("A001")
			.build();
		companyJpaRepository.save(CompanyEntity.from(company));

		CompanyCreate companyCreate = CompanyCreate.builder()
			.companyName("company")
			.businessRegistrationNumber("A002")
			.build();

		// when & then
		assertThatThrownBy(() -> companyService.register(companyCreate))
			.isInstanceOf(BusinessException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.ENTITY_ALREADY_EXIST);
	}
}