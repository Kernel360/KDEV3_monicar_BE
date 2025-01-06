package org.collector.domain;

import java.io.Serial;
import java.io.Serializable;

import org.collector.presentation.dto.CycleInfoRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class VehicleInformation implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private long id;
	private long mdn;
	private String tid;
	private long mid;
	private int pv;
	private long did;
	private String dFWVer;

	public static VehicleInformation from(CycleInfoRequest request) {
		return VehicleInformation.builder()
			.mdn(request.mdn())
			.tid(request.tid())
			.mid(request.mid())
			.pv(request.pv())
			.did(request.did())
			.build();
	}
}
