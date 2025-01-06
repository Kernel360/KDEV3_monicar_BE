package org.collector.domain;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.collector.presentation.dto.CListRequest;
import org.collector.presentation.dto.GCD;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CycleInfo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
	private LocalDateTime interval_at;
	@Enumerated(EnumType.STRING)
	private GCD gcd;
	private BigDecimal lat;
	private BigDecimal lon;
	private int ang;
	private int spd;
	private int sum;
	private int bat;
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private VehicleInformation vehicleInformation;

	public static BigDecimal convertToSixDecimalPlaces(Long value) {
		return BigDecimal.valueOf(value / 1000000.0);
	}

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
