package org.controlcenter.company.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.company.domain.Company;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "company")
public class CompanyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;

	private String companyName;

	private String businessRegistrationNumber;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static CompanyEntity from(Company company) {
		CompanyEntity companyEntity = new CompanyEntity();
		companyEntity.id = company.getId();
		companyEntity.companyName = company.getCompanyName();
		companyEntity.businessRegistrationNumber = company.getBusinessRegistrationNumber();
		companyEntity.createdAt = company.getCreatedAt();
		companyEntity.updatedAt = company.getUpdatedAt();
		companyEntity.deletedAt = company.getDeletedAt();
		return companyEntity;
	}

	public Company toDomain() {
		return Company.builder()
			.id(id)
			.companyName(companyName)
			.businessRegistrationNumber(businessRegistrationNumber)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
