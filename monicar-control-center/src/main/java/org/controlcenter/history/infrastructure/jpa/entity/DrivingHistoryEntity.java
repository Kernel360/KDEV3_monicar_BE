package org.controlcenter.history.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.history.domain.DrivingHistory;
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
@Entity(name = "driving_history")
public class DrivingHistoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driving_history_id")
	private Long id;

	private Long vehicleId;

	private Long departmentId;

	private String driverEmail;

	private Double initialOdometer;

	private Double finalOdometer;

	private Double drivingDistance;

	private Double businessCommuteDistance;

	private Double businessUsageDistance;

	private Boolean isBusinessUse;

	private LocalDateTime usedAt;

	private LocalDateTime onTime;

	private LocalDateTime offTime;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static DrivingHistoryEntity from(DrivingHistory drivingHistory) {
		DrivingHistoryEntity drivingHistoryEntity = new DrivingHistoryEntity();
		drivingHistoryEntity.id = drivingHistory.getId();
		drivingHistoryEntity.vehicleId = drivingHistory.getVehicleId();
		drivingHistoryEntity.departmentId = drivingHistory.getDepartmentId();
		drivingHistoryEntity.driverEmail = drivingHistory.getDriverEmail();
		drivingHistoryEntity.initialOdometer = drivingHistory.getInitialOdometer();
		drivingHistoryEntity.finalOdometer = drivingHistory.getFinalOdometer();
		drivingHistoryEntity.drivingDistance = drivingHistory.getDrivingDistance();
		drivingHistoryEntity.businessCommuteDistance = drivingHistory.getBusinessCommuteDistance();
		drivingHistoryEntity.businessUsageDistance = drivingHistory.getBusinessUsageDistance();
		drivingHistoryEntity.isBusinessUse = drivingHistory.getIsBusinessUse();
		drivingHistoryEntity.usedAt = drivingHistory.getUsedAt();
		drivingHistoryEntity.onTime = drivingHistory.getOnTime();
		drivingHistoryEntity.offTime = drivingHistory.getOffTime();
		drivingHistoryEntity.createdAt = drivingHistory.getCreatedAt();
		drivingHistoryEntity.updatedAt = drivingHistory.getUpdatedAt();
		drivingHistoryEntity.deletedAt = drivingHistory.getDeletedAt();
		return drivingHistoryEntity;
	}

	public DrivingHistory toDomain() {
		return DrivingHistory.builder()
			.id(id)
			.vehicleId(vehicleId)
			.departmentId(departmentId)
			.driverEmail(driverEmail)
			.initialOdometer(initialOdometer)
			.finalOdometer(finalOdometer)
			.drivingDistance(drivingDistance)
			.businessCommuteDistance(businessCommuteDistance)
			.businessUsageDistance(businessUsageDistance)
			.isBusinessUse(isBusinessUse)
			.usedAt(usedAt)
			.onTime(onTime)
			.offTime(offTime)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
