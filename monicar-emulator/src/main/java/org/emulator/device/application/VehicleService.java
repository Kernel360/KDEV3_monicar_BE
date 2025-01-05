package org.emulator.device.application;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.VehicleCommandSender;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleService {
	private final EmulatorRepository emulatorRepository;
	private final LocationReceiver locationReceiver;
	private final VehicleCommandSender vehicleCommandSender;

	public void onVehicle() {
		GpsTime onLocation = locationReceiver.getLocation();

		OnInfo onInfo = OnInfo.create(
			onLocation.intervalAt(),
			GpsStatus.A,
			onLocation.location().lat(),
			onLocation.location().lon(),
			emulatorRepository.getTotalDistance()
		);

		CommonResponse response = vehicleCommandSender.sendOnCommand(onInfo);
	}
}
