package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

import org.emulator.device.VehicleConstant;

/**
 * 차량의 기본 데이터를 담는 VO
 *
 * @field mdn     차량 번호 - 차량을 식별하는 고유 번호
 * @field num	  차량 번호 - 번호판 번호
 * @field tid     터미널 아이디 - 터미널 장치의 고유 식별자
 * @field mid     제조사 아이디 - 제조사를 구분하는 식별자
 * @field pv      패킷 버전 - 데이터 패킷의 버전 정보
 * @field did     디바이스 아이디 - 설치된 디바이스의 고유 식별자
 */
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
