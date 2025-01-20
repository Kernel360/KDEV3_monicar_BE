package org.controlcenter.vehicle.application;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventCreate;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.VehicleQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleService {
	private final VehicleQueryRepository vehicleQueryRepository;
	private final VehicleRepository vehicleRepository;
	private final VehicleEventRepository vehicleEventRepository;

	@Transactional(readOnly = true)
	public String getVehicleNumber(Long vehicleId) {
		VehicleInformation vehicleInformation = vehicleRepository.findById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
		return vehicleInformation.getVehicleNumber();
	}

	@Transactional(readOnly = true)
	public VehicleInformation getVehicleInformation(String vehicleNumber) {
		return vehicleRepository.findByVehicleNumber(vehicleNumber)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
	}

	@Transactional
	public VehicleEvent saveVehicleEvent(final VehicleEventCreate command) {
		return vehicleEventRepository.save(VehicleEvent.create(command));
	}

	@Transactional(readOnly = true)
	public VehicleInformation getVehicleInformation(final Long mdn) {
		return vehicleRepository.findByMdn(mdn)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

	}
}
