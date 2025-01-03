package org.controlcenter.geoinfo.infrastructure.jpa.entity;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.controlcenter.geoinfo.domain.CycleInfo;
import org.controlcenter.geoinfo.domain.GpsStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "cycle_info")
public class CycleInfoEntity {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cycle_info_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private GpsStatus status;

	private BigDecimal lat;

	private BigDecimal lon;

	private Integer ang;

	private Integer spd;

	private LocalDateTime intervalAt;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static CycleInfoEntity from(CycleInfo cycleInfo) {
		CycleInfoEntity cycleInfoEntity = new CycleInfoEntity();
		cycleInfoEntity.id = cycleInfo.getId();
		cycleInfoEntity.status = cycleInfo.getStatus();
		cycleInfoEntity.lat = cycleInfo.getLat();
		cycleInfoEntity.lon = cycleInfo.getLon();
		cycleInfoEntity.ang = cycleInfo.getAng();
		cycleInfoEntity.spd = cycleInfo.getSpd();
		cycleInfoEntity.intervalAt = cycleInfo.getIntervalAt();
		cycleInfoEntity.createdAt = cycleInfo.getCreatedAt();
		cycleInfoEntity.updatedAt = cycleInfo.getUpdatedAt();
		cycleInfoEntity.deletedAt = cycleInfo.getDeletedAt();
		return cycleInfoEntity;
	}

	public CycleInfo toDomain() {
		return CycleInfo.builder()
			.id(id)
			.status(status)
			.lat(lat)
			.lon(lon)
			.ang(ang)
			.spd(spd)
			.intervalAt(intervalAt)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
