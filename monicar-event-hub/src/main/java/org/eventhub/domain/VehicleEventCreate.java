package org.eventhub.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

import org.eventhub.common.exception.BusinessException;
import org.eventhub.common.response.ErrorCode;

@Getter
public class VehicleEventCreate {
	private static final int EXPECTED_ON_OFF_TIME_LENGTH = 14; // ccyyMMddHHmmss
	private static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";

	private final long vehicleId;
	private final VehicleEventType eventType;
	private final LocalDateTime eventAt;

	private VehicleEventCreate() {
		this.vehicleId = 0;
		this.eventType = null;
		this.eventAt = null;
	}

	private VehicleEventCreate(
			final long vehicleId,
			final VehicleEventType eventType,
			final LocalDateTime eventAt) {
		this.vehicleId = vehicleId;
		this.eventType = eventType;
		this.eventAt = eventAt;
	}

	public static VehicleEventCreate of(
			final long existedVehicleId,
			final int existedSum,
			final VehicleEventType onEvent,
			final String rawEventAt,
			final int sum) {
		LocalDateTime onTime = validateEventAt(rawEventAt);
		validateSum(existedSum, sum);

		return new VehicleEventCreate(
				existedVehicleId,
				onEvent,
				onTime
		);
	}

	private static void validateSum(final int existedSum, final int sum) {
		if (existedSum != sum) {
			throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
		}
	}

	private static LocalDateTime validateEventAt(final String rawEventAt) {
		validateFormat(rawEventAt);

		final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

		return LocalDateTime.parse(rawEventAt, dateTimeFormat);
	}

	private static void validateFormat(final String rawEventAt) {
		if (!isValidLength(rawEventAt) || !isNumeric(rawEventAt)) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	private static boolean isValidLength(final String rawEventAt) {
		return rawEventAt.length() == EXPECTED_ON_OFF_TIME_LENGTH;
	}

	private static boolean isNumeric(final String rawEventAt) {
		return rawEventAt.chars().allMatch(Character::isDigit);
	}
}
