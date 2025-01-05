package org.controlcenter.vehicle.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record VehicleInfoSearchRequest(
	@NotBlank(message = "차량 정보는 비어 있을 수 없습니다.")
	String vehicleNumber
) {
}