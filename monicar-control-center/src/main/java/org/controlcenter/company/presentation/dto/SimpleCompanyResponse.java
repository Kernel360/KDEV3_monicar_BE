package org.controlcenter.company.presentation.dto;

import java.time.LocalDateTime;

import org.controlcenter.company.domain.Company;

import lombok.Builder;

@Builder
public record SimpleCompanyResponse(
	Long id,
	String companyName,
	String businessRegistrationNumber,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	LocalDateTime deletedAt
) {
	public static SimpleCompanyResponse from(Company company) {
		return SimpleCompanyResponse.builder()
			.id(company.getId())
			.companyName(company.getCompanyName())
			.businessRegistrationNumber(company.getBusinessRegistrationNumber())
			.createdAt(company.getCreatedAt())
			.updatedAt(company.getUpdatedAt())
			.deletedAt(company.getDeletedAt())
			.build();
	}
}
