package org.eventhub.infrastructure.messaging;

import org.eventhub.infrastructure.messaging.command.CycleInfoListCommand;

public interface CycleInfoEventPublisher {
	void publishEvent(CycleInfoListCommand cycleInfoListCommand);
}
