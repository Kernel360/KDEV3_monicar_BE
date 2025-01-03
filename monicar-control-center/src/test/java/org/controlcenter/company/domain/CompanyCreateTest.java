package org.controlcenter.company.domain;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[domain 단위테스트] CompanyCreate")
class CompanyCreateTest {

	@DisplayName("회사를 생성할 수 있다.")
	@Test
	void createCompanyCreate() {
		// given
		String companyName = "company";
		String businessRegistrationNumber = "A001";

		// when
		CompanyCreate companyCreate = CompanyCreate.of(companyName, businessRegistrationNumber);

		// then
		assertAll(
			() -> assertThat(companyCreate.getCompanyName()).isEqualTo("company"),
			() -> assertThat(companyCreate.getBusinessRegistrationNumber()).isEqualTo("A001")
		);
	}

	@DisplayName("회사 이름이 10자 초과라면 생성할 수 없다.")
	@Test
	void createCompanyCreateWithInvalidCompanyName() {
		// given
		String invalidCompanyName = "a".repeat(11);
		String businessRegistrationNumber = "A001";

		// when & then
		assertThatThrownBy(() -> CompanyCreate.of(invalidCompanyName, businessRegistrationNumber))
			.isInstanceOf(BusinessException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.INVALID_INPUT_VALUE);
	}
}