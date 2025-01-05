package org.emulator.device.domain;

import java.time.LocalDateTime;

import org.emulator.device.infrastructure.external.command.vo.Direction;
import org.emulator.device.infrastructure.external.command.vo.Geo;
import org.emulator.device.infrastructure.external.command.vo.Speed;
import org.emulator.device.infrastructure.external.command.vo.TotalDistance;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OnInfo {
	private LocalDateTime onTime;
	private LocalDateTime offTime;
	private GpsStatus gpsStatus;
	private Geo geo;
	private Direction direction;
	private Speed speed;
	private TotalDistance totalDistance;

	public static OnInfo create(
		LocalDateTime onTime,
		GpsStatus gpsStatus,
		double latitude,
		double longitude,
		int totalDistance
	) {
		return OnInfo.builder()
			.onTime(onTime)
			.offTime(null)
			.gpsStatus(gpsStatus)
			.geo(new Geo(latitude, longitude))
			.direction(new Direction())
			.speed(new Speed())
			.totalDistance(new TotalDistance(totalDistance))
			.build();
	}
}
