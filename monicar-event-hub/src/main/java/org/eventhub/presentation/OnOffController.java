package org.eventhub.presentation;

import java.util.Optional;

import org.eventhub.application.DrivingHistoryService;
import org.eventhub.application.VehicleEventService;
import org.eventhub.application.VehicleService;
import org.eventhub.common.response.BaseResponse;
import org.eventhub.common.response.ErrorCode;
import org.eventhub.domain.DrivingHistoryCreate;
import org.eventhub.domain.UpdateTotalDistance;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventType;
import org.eventhub.domain.VehicleInformation;
import org.eventhub.domain.VehicleOffEventCreate;
import org.eventhub.domain.VehicleOnEventCreate;
import org.eventhub.domain.VehicleStatus;
import org.eventhub.presentation.request.KeyOffRequest;
import org.eventhub.presentation.request.KeyOnRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/event-hub")
@Slf4j
public class OnOffController {
	private final VehicleService vehicleService;
	private final VehicleEventService vehicleEventService;
	private final DrivingHistoryService drivingHistoryService;

	@PostMapping("/key-on")
	public BaseResponse keyOn(
		@Valid @RequestBody final KeyOnRequest request
	) {
		log.info("emulator ON request in event-hub ! ");
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(request.mdn());

		Optional<VehicleEvent> vehicleEvent = vehicleEventService.getRecentVehicleEvent(vehicleInformation.getId());
		boolean isAlreadyOn = vehicleEvent.map(VehicleEvent::isTypeOn).orElse(false);

		log.info("is already ON? : {}! {}", isAlreadyOn, isAlreadyOn ? "" : "Engine ON");
		if (isAlreadyOn) {
			return BaseResponse.fail(ErrorCode.WRONG_APPROACH);
		}
		VehicleOnEventCreate vehicleOnEventCreate = request.toDomain(vehicleInformation.getId(), vehicleInformation.getSum());
		vehicleEventService.saveVehicleEvent(vehicleOnEventCreate);

		vehicleService.updateVehicleStatus(vehicleInformation.getId(), VehicleStatus.IN_OPERATION);

		return BaseResponse.success();
	}

	@Transactional
	@PostMapping("/key-off")
	public BaseResponse keyOff(
		@Valid @RequestBody final KeyOffRequest request
	) {
		log.info("emulator OFF request in event-hub ! ");
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(request.mdn());

		Optional<VehicleEvent> vehicleEvent = vehicleEventService.getRecentVehicleEvent(vehicleInformation.getId());
		boolean isAlreadyOff = vehicleEvent.map(VehicleEvent::isTypeOff).orElse(false);

		log.info("is already OFF? : {}! {}", isAlreadyOff, isAlreadyOff ? "" : "Engine OFF");
		if (isAlreadyOff) {
			return BaseResponse.fail(ErrorCode.WRONG_APPROACH);
		}

		vehicleService.updateVehicleStatus(vehicleInformation.getId(), VehicleStatus.NOT_DRIVEN);

		Long updatedTotalDistance = vehicleService.updateTotalDistance(UpdateTotalDistance.of(
			vehicleInformation.getId(), request.sum()
		));

		drivingHistoryService.saveDrivingHistory(DrivingHistoryCreate.of(
			vehicleInformation.getId(),
			vehicleInformation.getCompanyId(),
			updatedTotalDistance,
			request.sum(),
			vehicleEvent.get().getEventAt(),
			request.offTime()
			)
		);

		vehicleEventService.saveVehicleEvent(VehicleOffEventCreate.of(
			vehicleInformation.getId(),
			VehicleEventType.OFF,
			request.offTime()
		));

		return BaseResponse.success();
	}
}
