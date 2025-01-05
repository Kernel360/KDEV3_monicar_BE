package org.emulator.device.infrastructure;

import java.util.List;

import org.emulator.device.domain.CycleInfo;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApplicationCycleInfoEventPublisher {
	private final ApplicationEventPublisher eventPublisher;

	public void publishEvent(List<CycleInfo> cycleInfoList) {
		eventPublisher.publishEvent(cycleInfoList);
	}
}
