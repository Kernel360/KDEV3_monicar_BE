package org.controlcenter.company.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("[domain 단위테스트] Company")
class CompanyTest {

	@DisplayName("쿠폰을 생성할 수 있다.")
	@Test
	void createCoupon() {
		// given
		CompanyCreate companyCreate = CompanyCreate.builder()
			.companyName("company")
			.businessRegistrationNumber("A001")
			.build();

		// when & then
		assertThatCode(() -> Company.create(companyCreate))
			.doesNotThrowAnyException();
	}

}