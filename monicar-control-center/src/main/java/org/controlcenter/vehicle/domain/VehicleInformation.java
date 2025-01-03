package org.controlcenter.vehicle.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleInformation {
	private Long id;
	private Long vehicleTypeId;
	private String mdn;
	private String tid;
	private Integer mid;
	private Integer pv;
	private Integer did;
	private Integer sum;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
