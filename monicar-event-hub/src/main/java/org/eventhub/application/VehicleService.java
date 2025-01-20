package org.eventhub.application;

import org.eventhub.application.port.VehicleEventRepository;
import org.eventhub.application.port.VehicleRepository;
import org.eventhub.common.exception.BusinessException;
import org.eventhub.common.response.ErrorCode;
import org.eventhub.domain.VehicleInformation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleService {
	private final VehicleRepository vehicleRepository;
	private final VehicleEventRepository vehicleEventRepository;

	@Transactional(readOnly = true)
	public String getVehicleNumber(Long vehicleId) {
		VehicleInformation vehicleInformation = vehicleRepository.findById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
		return vehicleInformation.getVehicleNumber();
	}

	@Transactional(readOnly = true)
	public VehicleInformation getVehicleInformation(final Long mdn) {
		return vehicleRepository.findByMdn(mdn)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

	}
}
