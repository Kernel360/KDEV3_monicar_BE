package org.eventhub.presentation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Builder;

import org.eventhub.domain.CycleInfo;
import org.eventhub.domain.GpsStatus;

@Builder
public record SimpleCycleInfoRequest(
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime intervalAt,
	GpsStatus gcd,
	Long lat,
	Long lng,
	Integer spd,
	Integer sum
) {
	public CycleInfo toDomain() {
		return CycleInfo.builder()
			.intervalAt(intervalAt)
			.gcd(gcd)
			.lat(lat)
			.lng(lng)
			.sum(sum)
			.build();
	}
}
