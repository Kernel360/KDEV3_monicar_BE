package org.controlcenter.geoinfo.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CycleInfo {
	private long id;
	private GpsStatus status;
	private BigDecimal lat;
	private BigDecimal lon;
	private Integer ang;
	private Integer spd;
	private LocalDateTime intervalAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static BigDecimal convertToSixDecimalPlaces(Double value) {
		return BigDecimal.valueOf(value / 1000000.0);
	}
}
