package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

@Getter
public class Direction {
	public static final int DIRECTION_MIN = 0;
	public static final int DIRECTION_MAX = 365;

	private final int value;

	public Direction() {
		this.value = 0;
	}

	public Direction(int value) {
		if (value < DIRECTION_MIN || value > DIRECTION_MAX)
			throw new IllegalArgumentException("direction out of range");
		this.value = value;
	}
}
