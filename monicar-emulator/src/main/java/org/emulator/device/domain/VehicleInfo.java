package org.emulator.device.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field vehicleOffInfoList  시동 OFF 시간 이력 목록
 */
public class VehicleInfo {
	private static final VehicleInfo INSTANCE = new VehicleInfo();

	private List<OffInfo> vehicleOffInfoList;
	private int totalDistance;

	private VehicleInfo() {
		vehicleOffInfoList = new ArrayList<>();
		totalDistance = 0;
	}

	public static VehicleInfo getInstance() {
		return INSTANCE;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public int updateTotalDistance(int distance) {
		totalDistance += distance;
		return totalDistance;
	}
}
