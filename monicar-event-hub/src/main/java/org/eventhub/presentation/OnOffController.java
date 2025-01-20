package org.eventhub.presentation;

import org.common.dto.CommonResponse;
import org.eventhub.application.VehicleEventService;
import org.eventhub.application.VehicleService;
import org.eventhub.common.response.BaseResponse;
import org.eventhub.common.response.EmulatorResponseCode;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventCreate;
import org.eventhub.domain.VehicleInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/event-hub")
public class OnOffController {
	private final VehicleService vehicleService;
	private final VehicleEventService vehicleEventService;

	@PostMapping("/key-on")
	public BaseResponse<CommonResponse> keyOn(
		@Valid @RequestBody final KeyOnRequest request
	) {
		/*
		 * TODO 토큰 확인 로직 추가
		 */
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(request.mdn());

		VehicleEvent vehicleEvent = vehicleEventService.getRecentVehicleEvent(vehicleInformation.getId());
		if (vehicleEvent.isTypeOn()) {
			return BaseResponse.emulatorFail(EmulatorResponseCode.WRONG_APPROACH, request.mdn());
		}

		VehicleEventCreate vehicleEventCreate = request.toDomain(vehicleInformation.getId(), vehicleInformation.getSum());
		vehicleEventService.saveVehicleEvent(vehicleEventCreate);

		return BaseResponse.emulatorSuccess(request.mdn());
	}
}
