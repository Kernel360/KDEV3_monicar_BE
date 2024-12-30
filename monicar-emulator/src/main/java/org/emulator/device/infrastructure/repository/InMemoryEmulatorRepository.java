package org.emulator.device.infrastructure.repository;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.domain.VehicleInfo;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEmulatorRepository implements EmulatorRepository {
    private VehicleInfo vehicleInfo = new VehicleInfo();

	@Override
	public int getTotalDistance() {
		return vehicleInfo.getTotalDistance();
	}

	@Override
	public String getGpsStatus() {
		return vehicleInfo.getGpsStatus();
	}
}
