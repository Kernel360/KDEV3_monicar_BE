package org.eventhub.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleInformation {
	private Long id;
	private Long companyId;
	private Long vehicleTypeId;
	private String vehicleNumber;
	private Long mdn;
	private String tid;
	private Integer mid;
	private Integer pv;
	private Integer did;
	private Integer drivingDays;
	private Integer sum;
	private VehicleStatus status;
	private LocalDate deliveryDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
