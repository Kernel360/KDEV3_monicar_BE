package org.emulator.device.infrastructure.external.command;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Builder;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.GpsStatus;

/**
 * 주기 정보 데이터 리스트 레코드
 *
 * @param intervalAt  발생 시간 (초) - 시간 값을 초 단위로 나타냄 (형식: 'ss')
 * @param gcd  GPS 상태 - GPS 수신 상태 ('A': 정상, 'V': 비정상, '0': 미장착)
 * @param lat  GPS 위도 - 위도 * 1000000 (소수점 6자리, 단위: 위도 값)
 * @param lng  GPS 경도 - 경도 * 1000000 (소수점 6자리, 단위: 경도 값)
 * @param ang  방향 - 차량의 현재 진행 방향 (범위: 0 ~ 365)
 * @param spd  속도 - 차량의 현재 속도 (범위: 0 ~ 255, 단위: km/h)
 * @param sum  누적 주행 거리 - 차량의 현재 총 주행 거리 (범위: 0 ~ 9999999, 단위: m)
 */
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
	public static CycleInfoCommand from(CycleInfo cycleInfo) {
		return CycleInfoCommand.builder()
			.intervalAt(cycleInfo.getIntervalAt())
			.gcd(cycleInfo.getGpsStatus())
			.lat(cycleInfo.getGeo().getLatitude())
			.lng(cycleInfo.getGeo().getLongitude())
			.ang(cycleInfo.getDirection().getValue())
			.spd(cycleInfo.getSpeed().getValue())
			.sum(cycleInfo.getTotalDistance().getValue())
			.build();
	}
}
