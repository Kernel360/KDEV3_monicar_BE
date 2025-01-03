package org.controlcenter.company.domain;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyCreate {
	public static final int MAX_COMPANY_SIZE = 10;

	private final String companyName;
	private final String businessRegistrationNumber;

	private CompanyCreate(
		String companyName,
		String businessRegistrationNumber
	) {
		this.companyName = companyName;
		this.businessRegistrationNumber = businessRegistrationNumber;
	}

	public static CompanyCreate of(
		String companyName,
		String businessRegistrationNumber
	) {
		validateCompanyNameSize(companyName);

		return new CompanyCreate(
			companyName,
			businessRegistrationNumber
		);
	}

	private static void validateCompanyNameSize(String companyName) {
		if (companyName.length() > MAX_COMPANY_SIZE) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
