package org.collector.presentation.dto;

import java.time.LocalDateTime;

import org.collector.domain.CycleInfo;
import org.collector.domain.VehicleInformation;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record CListRequest(
	@NotNull(message = "발생시간은 필수 입력값입니다.")
	@JsonFormat(pattern = "yyyyMMddHHmmss")
	LocalDateTime interval_at,

	@NotNull(message = "유효하지 않은 GPS상태 값이 입력되었습니다.")
	GCD gcd,

	@NotNull(message = "GPS 위도는 필수 입력값입니다.")
	@Range(min = -90000000, max = 90000000, message = "위도는 -90백만 이상, 90백만 이하의 값이어야합니다.")
	Integer lat,

	@NotNull(message = "GPS 경도는 필수 입력값입니다.")
	@Range(min = -180000000, max = 180000000, message = "경도는 -180백만 이상, 180백만 이하의 값이어야합니다.")
	Integer lon,

	@Range(min = 0, max = 365, message = "방향은 0 ~ 365 사이여야 합니다.")
	@NotNull(message = "방향은 필수 입력값입니다.")
	Integer ang,

	@NotNull(message = "속도는 필수 입력값입니다.")
	@Range(min = 0, max = 255, message = "속도는 0 ~ 255 사이여야 합니다.")
	Integer spd,

	@NotNull(message = "누적 주행거리는 필수 입력값입니다.")
	@Range(min = 0, max = 9999999, message = "누적 주행거리는 0 ~ 9999999 사이여야 합니다.")
	Integer sum,

	@NotNull(message = "배터리 전압은 필수 입력값입니다.")
	@Range(min = 0, max = 9999, message = "배터리 전압은 0 ~ 9999 사이여야 합니다.")
	Integer bat
) {
	public static CycleInfo from(CListRequest request, VehicleInformation vehicleInformation) {
		return CycleInfo.builder()
			.interval_at(request.interval_at())
			.gcd(request.gcd())
			.lat(CycleInfo.convertToSixDecimalPlaces(request.lat()))
			.lon(CycleInfo.convertToSixDecimalPlaces(request.lon()))
			.ang(request.ang())
			.spd(request.spd())
			.sum(request.sum())
			.bat(request.bat())
			.vehicleInformation(vehicleInformation)
			.build();
	}
}
