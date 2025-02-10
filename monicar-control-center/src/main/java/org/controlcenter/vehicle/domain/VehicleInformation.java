package org.controlcenter.vehicle.domain;

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

	public static VehicleInformation create(VehicleRegister vehicleRegister) {
		return VehicleInformation.builder()
			.companyId(1L)
			.vehicleTypeId(vehicleRegister.getVehicleTypeId())
			.vehicleNumber(vehicleRegister.getVehicleNumber())
			.mdn(1234567890L)
			.tid("TID001")
			.mid(1)
			.pv(1)
			.did(1)
			.drivingDays(0)
			.sum(vehicleRegister.getDrivingDistance())
			.status(VehicleStatus.NOT_DRIVEN)
			.deliveryDate(vehicleRegister.getDeliveryDate())
			.build();
	}

	public boolean isInOperation() {
		return status == VehicleStatus.IN_OPERATION;
	}
}