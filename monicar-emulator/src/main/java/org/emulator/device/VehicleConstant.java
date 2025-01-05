package org.emulator.device;

public final class VehicleConstant {
	public static final long VEHICLE_PRIMARY_KEY = 1234567890L;
	public static final String VEHICLE_NUMBER = "12나3456";
	public static final String TERMINAL_ID = "A001";
	public static final long MANUFACTURER_ID = 6L;
	public static final int PACKET_VERSION = 5;
	public static final long DEVICE_ID = 1L;
	public static final int BATTERY = 100;

	public static final long MIL = 1_000_000;

	private VehicleConstant() {
		throw new IllegalAccessError("Constant class");
	}
}
