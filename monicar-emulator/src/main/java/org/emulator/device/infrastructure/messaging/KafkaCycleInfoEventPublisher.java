package org.emulator.device.infrastructure.messaging;

import org.emulator.device.application.port.CycleInfoEventPublisher;
import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;
import org.springframework.stereotype.Component;

@Component
public class KafkaCycleInfoEventPublisher implements CycleInfoEventPublisher {
	@Override
	public void publishEvent(CycleInfoListCommand cycleInfoListCommand) {
		// TODO: kafka 이벤트 발행
	}
}
