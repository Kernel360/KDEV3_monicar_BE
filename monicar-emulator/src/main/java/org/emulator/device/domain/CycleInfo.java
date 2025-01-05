package org.emulator.device.domain;

import java.time.LocalDateTime;

import org.emulator.device.VehicleConstant;
import org.emulator.device.infrastructure.GpsTime;
import org.emulator.device.infrastructure.external.command.vo.Direction;
import org.emulator.device.infrastructure.external.command.vo.Geo;
import org.emulator.device.infrastructure.external.command.vo.Speed;
import org.emulator.device.infrastructure.external.command.vo.TotalDistance;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CycleInfo {
	private LocalDateTime intervalAt;
	private GpsStatus gpsStatus;
	private Geo geo;
	private Direction direction;
	private Speed speed;
	private TotalDistance totalDistance;
	private int battery;

	public static CycleInfo create(
		GpsTime gpsTime,
		GpsStatus gpsStatus,
		int direction,
		int speed,
		int totalDistance
	) {
		return CycleInfo.builder()
			.intervalAt(gpsTime.intervalAt())
			.gpsStatus(gpsStatus)
			.geo(new Geo(gpsTime.location().lat(), gpsTime.location().lon()))
			.direction(new Direction(direction))
			.speed(new Speed(speed))
			.totalDistance(new TotalDistance(totalDistance))
			.battery(VehicleConstant.BATTERY)
			.build();
	}
}
