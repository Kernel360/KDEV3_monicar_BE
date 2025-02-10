package org.emulator.sensor;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.VehicleEventSender;
import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;
import org.emulator.sensor.util.CycleTimeProvider;
import org.emulator.sensor.util.MovementCalculator;
import org.emulator.sensor.dto.GpsTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GpsTracker implements SensorTracker {
	private final EmulatorRepository emulatorRepository;
	private final LocationReceiver locationReceiver;
	private final VehicleEventSender vehicleEventSender;
	private final Map<String, MovementCalculator> calculators;

	private final CycleTimeProvider timeProvider;
	private final Deque<CycleInfo> cycleInfos = new LinkedList<>();
	private CycleInfo recentCycleInfo;

	@Scheduled(initialDelay = 3000, fixedDelay = 1000)
	@Override
	public void track() {
		int time = timeProvider.getCycleTime();

		if (isReadyToSendCycleInfo(time)) {
			List<CycleInfo> cycleInfoList = pollFromDeque(time); // poll all로 바꾸기(네트워크 지연되면 더 쌓여있을 수 있음)
			BaseResponse response = vehicleEventSender.sendCycleInfoEvent(cycleInfoList);

			log.info("POST request complete with response : {}", response.isSuccess());
			// TODO: 장애 처리 - deque 앞으로 다시 밀어 넣기, 재시도 등
		}

		GpsTime currentLocation = locationReceiver.fetchLocationNow();
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

		int intervalDistance = getDistance(recentCycleInfo, currentLocation);
		emulatorRepository.updateCurrentDistance(intervalDistance);

		CycleInfo currentCycleInfo = CycleInfo.create(
			currentLocation,
			GpsStatus.A,
			getDirection(recentCycleInfo, currentLocation),
			getSpeed(recentCycleInfo, currentLocation),
			intervalDistance
		);
		cycleInfos.offerLast(currentCycleInfo);
		recentCycleInfo = currentCycleInfo;

		log.info("collecting GPS. . .");
		log.info(" 💌cycle info lat: {}, lng: {}", currentLocation.location().lat(), currentLocation.location().lng());
		log.info(" 💌interval distance: {}", intervalDistance);
	}

	private boolean isReadyToSendCycleInfo(int time) {
		return (!cycleInfos.isEmpty()) && (cycleInfos.size() >= time);
	}

	private List<CycleInfo> pollFromDeque(int size) {
		List<CycleInfo> result = new ArrayList<>();
		while (result.size() < size) {
			CycleInfo cycleInfo = cycleInfos.pollFirst();
			if (Objects.nonNull(cycleInfo)) {
				result.add(cycleInfo);
			}
		}
		return result;
	}

	private int getDirection(CycleInfo preInfo, GpsTime curInfo) {
		MovementCalculator directionCalculator = calculators.get("directionCalculator");
		return directionCalculator.calculate(preInfo, curInfo);
	}

	private int getSpeed(CycleInfo preInfo, GpsTime curInfo) {
		MovementCalculator speedCalculator = calculators.get("speedCalculator");
		return speedCalculator.calculate(preInfo, curInfo);
	}

	private int getDistance(CycleInfo preInfo, GpsTime curInfo) {
		MovementCalculator distanceCalculator = calculators.get("distanceCalculator");
		return distanceCalculator.calculate(preInfo, curInfo);
	}
}
