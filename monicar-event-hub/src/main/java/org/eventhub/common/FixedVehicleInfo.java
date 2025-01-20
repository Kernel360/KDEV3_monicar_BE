package org.eventhub.common;

import org.eventhub.common.constant.VehicleConstant;

import lombok.Getter;

@Getter
public class FixedVehicleInfo {
	private static final FixedVehicleInfo INSTANCE = new FixedVehicleInfo();

	private final long mdn;
	private final String num;
	private final String tid;
	private final long mid;
	private final int pv;
	private final long did;

	private FixedVehicleInfo() {
		this.mdn = VehicleConstant.VEHICLE_PRIMARY_KEY;
		this.num = VehicleConstant.VEHICLE_NUMBER;
		this.tid = VehicleConstant.TERMINAL_ID;
		this.mid = VehicleConstant.MANUFACTURER_ID;
		this.pv = VehicleConstant.PACKET_VERSION;
		this.did = VehicleConstant.DEVICE_ID;
	}

	public static FixedVehicleInfo getInstance() {
		return INSTANCE;
	}
}
