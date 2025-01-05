package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

@Getter
public class Speed {
	private static final int SPEED_MIN = 0;
	private static final int SPEED_MAX = 255;

	private final int value;

	public Speed() {
		this.value = 0;
	}

	public Speed(int value) {
		if (value < SPEED_MIN || value > SPEED_MAX)
			throw new IllegalArgumentException("speed out of range");
		this.value = value;
	}
}
