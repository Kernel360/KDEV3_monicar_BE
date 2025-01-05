package org.emulator.device.infrastructure.external.command;

import java.time.LocalDateTime;

import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.vo.FixedVehicleInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

/**
 * 차량의 시동 ON / OFF 상태에서 전달하는 데이터를 담는 레코드
 *
 * @param mdn     차량 번호 - 차량을 식별하는 고유 번호
 * @param tid     터미널 아이디 - 터미널 장치의 고유 식별자
 * @param mid     제조사 아이디 - 제조사를 구분하는 식별자
 * @param pv      패킷 버전 - 데이터 패킷의 버전 정보
 * @param did     디바이스 아이디 - 설치된 디바이스의 고유 식별자
 * @param onTime  시동 ON 시간
 * @param offTime 직전 시동 OFF 시간
 * @param gcd     GPS 상태 - GPS 수신 상태 ('A': 정상, 'V': 비정상, '0': 미장착, 시동 on시 비정상: 'P')
 * @param lat     GPS 위도 - 차량의 현재 위도 좌표
 * @param lon     GPS 경도 - 차량의 현재 경도 좌표
 * @param ang     방향 - 차량의 현재 진행 방향
 * @param spd     속도 - 차량의 현재 속도
 * @param sum     누적 주행 거리 - 차량의 현재 총 주행 거리
 */
@Builder
public record OnCommand(
	long mdn,
	String tid,
	long mid,
	int pv,
	long did,
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime onTime,
	@JsonFormat(pattern = "yyyyMMddHHmmss") LocalDateTime offTime,
	GpsStatus gcd,
	long lat,
	long lon,
	int ang,
	int spd,
	int sum
) {
	public static OnCommand from(OnInfo onInfo) {
		FixedVehicleInfo fixedInfo = FixedVehicleInfo.getInstance();
		return OnCommand.builder()
			.mdn(fixedInfo.getMdn())
			.tid(fixedInfo.getTid())
			.mid(fixedInfo.getMid())
			.pv(fixedInfo.getPv())
			.did(fixedInfo.getDid())
			.onTime(onInfo.getOnTime())
			.offTime(onInfo.getOffTime())
			.gcd(onInfo.getGpsStatus())
			.ang(onInfo.getDirection().getValue())
			.spd(onInfo.getSpeed().getValue())
			.sum(onInfo.getTotalDistance().getValue())
			.build();
	}
}
