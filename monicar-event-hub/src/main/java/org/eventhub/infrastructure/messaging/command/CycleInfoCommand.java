package org.eventhub.infrastructure.messaging.command;

import java.time.LocalDateTime;

import org.eventhub.domain.GpsStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record CycleInfoCommand(
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime intervalAt,
	GpsStatus gcd,
	long lat,
	long lng,
	int ang,
	int spd,
	int sum
) {
}
