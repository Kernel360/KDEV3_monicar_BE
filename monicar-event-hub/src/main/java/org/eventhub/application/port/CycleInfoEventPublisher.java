package org.eventhub.application.port;

import org.eventhub.domain.CycleInfoList;

public interface CycleInfoEventPublisher {
	void publishEvent(CycleInfoList cycleInfoList);
}
