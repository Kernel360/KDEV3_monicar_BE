package org.emulator.device.application.port;

/**
 * 차량 정보 저장 역할
 */
public interface EmulatorRepository {
	int getTotalDistance();
	int updateTotalDistance(int distance);
}
