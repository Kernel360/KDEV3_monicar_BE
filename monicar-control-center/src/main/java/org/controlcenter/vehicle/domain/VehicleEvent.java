package org.controlcenter.vehicle.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleEvent {
	private Long id;
	private Long vehicleId;
	private VehicleEventType type;
	private LocalDateTime eventAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static VehicleEvent create(VehicleEventCreate vehicleEventCreate) {
		return VehicleEvent.builder()
			.vehicleId(vehicleEventCreate.getVehicleId())
			.type(vehicleEventCreate.getEventType())
			.eventAt(vehicleEventCreate.getEventAt())
			.build();
	}
}
