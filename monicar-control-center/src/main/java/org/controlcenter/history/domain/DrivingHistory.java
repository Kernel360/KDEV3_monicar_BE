package org.controlcenter.history.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DrivingHistory {
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
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
