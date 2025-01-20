package org.eventhub.application;

import org.eventhub.application.port.VehicleEventRepository;
import org.eventhub.common.exception.BusinessException;
import org.eventhub.common.response.ErrorCode;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleEventService {
	private final VehicleEventRepository vehicleEventRepository;

	@Transactional
	public VehicleEvent saveVehicleEvent(final VehicleEventCreate command) {
		return vehicleEventRepository.save(VehicleEvent.create(command));
	}

	@Transactional(readOnly = true)
	public VehicleEvent getRecentVehicleEvent(long vehicleId) {
		return vehicleEventRepository
			.findLatestById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
	}
}
