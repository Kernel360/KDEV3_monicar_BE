package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

import org.emulator.device.VehicleConstant;

@Getter
public class Geo {
	private static final double LATITUDE_MIN = -80.000000;
	private static final double LATITUDE_MAX = 80.000000;
	private static final double LONGITUDE_MIN = -180.000000;
	private static final double LONGITUDE_MAX = 180.000000;

	private final long latitude;
	private final long longitude;

	public Geo() {
		this.latitude = 0L;
		this.longitude = 0L;
	}

	public Geo(double latitude, double longitude) {
		if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX)
			throw new IllegalArgumentException("latitude out of range");
		if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX)
			throw new IllegalArgumentException("longitude out of range");
		this.latitude =(long)(latitude * VehicleConstant.MIL);
		this.longitude = (long)(longitude * VehicleConstant.MIL);
	}
}
