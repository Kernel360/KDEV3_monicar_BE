package org.emulator.device.infrastructure;

import lombok.RequiredArgsConstructor;

import org.emulator.device.application.port.CycleInfoEventPublisher;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ApplicationCycleInfoEventListener {
	private final CycleInfoEventPublisher publisher;

	@EventListener
	@Async
	public void publishCycleInfoCommand(List<CycleInfo> cycleInfoList) {
		CycleInfoListCommand cycleInfoListCommand = CycleInfoListCommand.from(cycleInfoList);

		// publish CycleInfoListCommand
	}
}
