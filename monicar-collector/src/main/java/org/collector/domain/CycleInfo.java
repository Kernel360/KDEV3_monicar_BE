package org.collector.domain;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.collector.presentation.dto.CListRequest;
import org.collector.presentation.dto.GCD;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
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
	@Column(name = "cycle_info_id")
	private long id;

	private LocalDateTime intervalAt;

	@Enumerated(EnumType.STRING)
	private GCD status;

	private int lat;
	private int lng;
	private int ang;
	private int spd;

	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private VehicleInformation vehicleInformation;

	@CreatedDate
	private LocalDateTime createdAt;

	public static CycleInfo from(CListRequest request, VehicleInformation vehicleInformation) {
		return CycleInfo.builder()
			.intervalAt(request.intervalAt())
			.status(request.gcd())
			.lat(request.lat())
			.lng(request.lng())
			.ang(request.ang())
			.spd(request.spd())
			.vehicleInformation(vehicleInformation)
			.build();
	}
}
