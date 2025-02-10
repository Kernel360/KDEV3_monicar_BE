package org.controlcenter.vehicle.application;

import java.time.LocalDateTime;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.history.infrastructure.jpa.DrivingHistoryJpaRepository;
import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventCreate;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.VehicleRegister;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleService {
	private final VehicleRepository vehicleRepository;
	private final VehicleEventRepository vehicleEventRepository;
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;
	private final DrivingHistoryJpaRepository drivingHistoryJpaRepository;

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

	@Transactional(readOnly = true)
	public VehicleInformation getVehicleInformation(Long vehicleId) {
		return vehicleRepository.findById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
	}

	@Transactional
	public VehicleEvent saveVehicleEvent(final VehicleEventCreate command) {
		return vehicleEventRepository.save(VehicleEvent.create(command));
	}

	@Transactional
	public VehicleInformation register(VehicleRegister command) {
		validateAlreadyExistVehicleNumber(command.getVehicleNumber());

		return vehicleRepository.save(VehicleInformation.create(command));
	}

	private void validateAlreadyExistVehicleNumber(String vehicleNumber) {
		vehicleRepository.findByVehicleNumber(vehicleNumber).ifPresent(existVehicleNumber -> {
			throw new BusinessException(ErrorCode.VEHICLE_ALREADY_EXIST);
		});
	}

	@Transactional
	public void deleteVehicle(Long vehicleId) {
		vehicleRepository.findById(vehicleId).orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

		LocalDateTime now = LocalDateTime.now();

		vehicleInformationJpaRepository.softDeleteById(vehicleId, now);
		drivingHistoryJpaRepository.softDeleteById(vehicleId, now);
	}

	@Transactional(readOnly = true)
	public boolean isVehicleInOperation(Long vehicleId) {
		VehicleInformation vehicleInformation = vehicleRepository.findById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

		return vehicleInformation.isInOperation();
	}
}
