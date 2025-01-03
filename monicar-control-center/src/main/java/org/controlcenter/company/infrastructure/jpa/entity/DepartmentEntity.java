package org.controlcenter.company.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.company.domain.Department;
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
@Entity(name = "department")
public class DepartmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Long id;

	private Long companyId;

	private String departmentName;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static DepartmentEntity from(Department department) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.id = department.getId();
		departmentEntity.companyId = department.getCompanyId();
		departmentEntity.departmentName = department.getDepartmentName();
		departmentEntity.createdAt = department.getCreatedAt();
		departmentEntity.updatedAt = department.getUpdatedAt();
		departmentEntity.deletedAt = department.getDeletedAt();
		return departmentEntity;
	}

	public Department toDomain() {
		return Department.builder()
			.id(id)
			.companyId(companyId)
			.departmentName(departmentName)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
