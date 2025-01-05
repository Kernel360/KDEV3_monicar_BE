package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

@Getter
public class TotalDistance {
	public static final int TOTAL_DISTANCE_MIN = 0;
	public static final int TOTAL_DISTANCE_MAX = 9999999;

	private final int value;

	public TotalDistance() {
		this.value = 0;
	}

	public TotalDistance(int value) {
		if (value < TOTAL_DISTANCE_MIN || value > TOTAL_DISTANCE_MAX)
			throw new IllegalArgumentException("totalDistance out of range");
		this.value = value;
	}
}
