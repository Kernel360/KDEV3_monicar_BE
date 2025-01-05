package org.emulator.device.infrastructure;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.TransmissionTimeProvider;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.infrastructure.util.Calculator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GpsTracker implements SensorTracker {
	private final LocationReceiver locationReceiver;
	private final EmulatorRepository emulatorRepository;
	private final TransmissionTimeProvider timeProvider;
	private final ApplicationCycleInfoEventPublisher applicationEventPublisher;

	private final Deque<CycleInfo> cycleInfos = new LinkedList<>();
	private CycleInfo recentCycleInfo;

	@Scheduled(initialDelay = 3000, fixedDelay = 1000)
	@Override
	public void track() {
		int time = timeProvider.getTransmissionTime();

		if (cycleInfos.size() > time) {
			List<CycleInfo> cycleInfoList = pollFromDeque(time);
			applicationEventPublisher.publishEvent(cycleInfoList);

			log.info("[Thread: {}] {}", Thread.currentThread().getName(), "sending!");
		}

		GpsTime currentLocation = locationReceiver.getLocation();
		if (recentCycleInfo == null) {
			CycleInfo currentCycleInfo = CycleInfo.create(
				currentLocation,
				GpsStatus.A,
				0,
				0,
				0
			);
			cycleInfos.offerLast(currentCycleInfo);
			recentCycleInfo = currentCycleInfo;
			return;
		}

		int direction = getDirection(recentCycleInfo, currentLocation);
		int distance = getDistance(recentCycleInfo, currentLocation);
		int speed = getSpeed(distance, recentCycleInfo, currentLocation);

		CycleInfo currentCycleInfo = CycleInfo.create(
			currentLocation,
			GpsStatus.A,
			direction,
			speed,
			distance
		);
		cycleInfos.offerLast(currentCycleInfo);
		recentCycleInfo = currentCycleInfo;

		emulatorRepository.updateTotalDistance(distance);

		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "collecting data. . .");
	}

	private List<CycleInfo> pollFromDeque(int size) {
		List<CycleInfo> result = new ArrayList<>();
		while (!cycleInfos.isEmpty() && result.size() < size) {
			result.add(cycleInfos.pollFirst());
		}
		return result;
	}

	private int getDistance(CycleInfo preInfo, GpsTime curInfo) {
		return Calculator.calculateDistance(
			preInfo.getGeo().getLatitude(),
			preInfo.getGeo().getLongitude(),
			curInfo.location().lat(),
			curInfo.location().lon()
		);
	}

	private int getSpeed(int distance, CycleInfo preInfo, GpsTime curInfo) {
		Duration duration = Duration.between(preInfo.getIntervalAt(), curInfo.intervalAt());
		return Calculator.calculateSpeed(distance, duration.toMillis());
	}

	private int getDirection(CycleInfo preInfo, GpsTime curInfo) {
		return Calculator.calculateDirection(
			preInfo.getGeo().getLatitude(),
			preInfo.getGeo().getLongitude(),
			curInfo.location().lat(),
			curInfo.location().lon()
		);
	}
}
