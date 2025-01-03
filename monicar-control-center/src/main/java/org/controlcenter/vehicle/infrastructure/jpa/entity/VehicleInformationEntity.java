package org.controlcenter.vehicle.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.vehicle.domain.VehicleInformation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "vehicle_information")
public class VehicleInformationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Long id;

	private Long vehicleTypeId;

	private String mdn;

	private String tid;

	private Integer mid;

	private Integer pv;

	private Integer did;

	private Integer sum;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static VehicleInformationEntity from(VehicleInformation vehicleInformation) {
		VehicleInformationEntity vehicleInformationEntity = new VehicleInformationEntity();
		vehicleInformationEntity.id = vehicleInformation.getId();
		vehicleInformationEntity.vehicleTypeId = vehicleInformation.getVehicleTypeId();
		vehicleInformationEntity.mdn = vehicleInformation.getMdn();
		vehicleInformationEntity.tid = vehicleInformation.getTid();
		vehicleInformationEntity.mid = vehicleInformation.getMid();
		vehicleInformationEntity.pv = vehicleInformation.getPv();
		vehicleInformationEntity.did = vehicleInformation.getDid();
		vehicleInformationEntity.sum = vehicleInformation.getSum();
		vehicleInformationEntity.createdAt = vehicleInformation.getCreatedAt();
		vehicleInformationEntity.updatedAt = vehicleInformation.getUpdatedAt();
		vehicleInformationEntity.deletedAt = vehicleInformation.getDeletedAt();
		return vehicleInformationEntity;
	}

	public VehicleInformation toDomain() {
		return VehicleInformation.builder()
			.id(id)
			.vehicleTypeId(vehicleTypeId)
			.mdn(mdn)
			.tid(tid)
			.mid(mid)
			.pv(pv)
			.did(did)
			.sum(sum)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
