package org.eventhub.infrastructure.messaging.command;

import java.time.LocalDateTime;

import org.eventhub.domain.CycleInfo;
import org.eventhub.domain.GpsStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record CycleInfoCommand(
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime intervalAt,
	GpsStatus gcd,
	Long lat,
	Long lng,
	Integer ang,
	Integer spd,
	Integer sum
) {
	public static CycleInfoCommand from(CycleInfo cycleInfo) {
		return CycleInfoCommand.builder()
			.intervalAt(cycleInfo.getIntervalAt())
			.gcd(cycleInfo.getGcd())
			.lat(cycleInfo.getLat())
			.lng(cycleInfo.getLng())
			.ang(cycleInfo.getAng())
			.spd(cycleInfo.getSpd())
			.sum(cycleInfo.getSum())
			.build();
	}
}
