package org.emulator.device.infrastructure;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.RequiredArgsConstructor;

import org.emulator.device.application.port.LocationReceiver;
import org.emulator.pipe.Gps;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GpsPipeQueueReceiver implements LocationReceiver {
	private final ConcurrentLinkedQueue<Gps> gpsPipeQueue;

	@Retryable(maxAttempts = 1, backoff = @Backoff(delay = 3000))
	@Override
	public GpsTime getLocation() {
		Gps gps = Objects.requireNonNull(
			gpsPipeQueue.poll(),
			"No Gps data found. Check sensor"
		);
		return GpsTime.create(gps);
	}
}
