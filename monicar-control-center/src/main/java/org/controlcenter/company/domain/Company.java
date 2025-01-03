package org.controlcenter.company.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Company {
	private Long id;
	private String companyName;
	private String businessRegistrationNumber;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

    public static Company create(CompanyCreate companyCreate) {
        return Company.builder()
                .companyName(companyCreate.getCompanyName())
                .businessRegistrationNumber(companyCreate.getBusinessRegistrationNumber())
                .build();
    }
}
