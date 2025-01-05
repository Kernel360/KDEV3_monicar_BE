package org.controlcenter.vehicle.presentation.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record VehicleInfoResponse(
	Long vehicleId,
	String vehicleNumber,
	LocalDateTime firstDataAt,
	LocalDateTime lastDataAt
) {
}
