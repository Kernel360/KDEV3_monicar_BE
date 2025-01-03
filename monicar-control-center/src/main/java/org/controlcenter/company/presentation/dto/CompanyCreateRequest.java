package org.controlcenter.company.presentation.dto;

import org.controlcenter.company.domain.CompanyCreate;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CompanyCreateRequest(
	@NotBlank(message = "회사 이름은 null 또는 비어 있을 수 없습니다.")
	String companyName,

	@NotBlank(message = "회사 등록 번호는 null 또는 비어 있을 수 없습니다.")
	String businessRegistrationNumber
) {
	public CompanyCreate toDomain() {
		return CompanyCreate.of(
			companyName,
			businessRegistrationNumber
		);
	}
}
