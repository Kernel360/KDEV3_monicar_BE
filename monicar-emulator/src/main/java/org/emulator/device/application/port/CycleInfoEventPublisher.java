package org.emulator.device.application.port;

import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;

/**
 * 주기 정보 이벤트 발행 역할
 */
public interface CycleInfoEventPublisher {
	void publishEvent(CycleInfoListCommand cycleInfoListCommand);
}
