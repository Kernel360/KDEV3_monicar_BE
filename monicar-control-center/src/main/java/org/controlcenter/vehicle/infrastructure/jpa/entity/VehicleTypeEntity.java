package org.controlcenter.vehicle.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.vehicle.domain.VehicleType;
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
@Entity(name = "vehicle_types")
public class VehicleTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_types_id")
	private Long id;

	private String vehicleTypesName;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static VehicleTypeEntity from(VehicleType vehicleType) {
		VehicleTypeEntity vehicleTypeEntity = new VehicleTypeEntity();
		vehicleTypeEntity.id = vehicleType.getId();
		vehicleTypeEntity.vehicleTypesName = vehicleType.getVehicleTypesName();
		vehicleTypeEntity.createdAt = vehicleType.getCreatedAt();
		vehicleTypeEntity.updatedAt = vehicleType.getUpdatedAt();
		vehicleTypeEntity.deletedAt = vehicleType.getDeletedAt();
		return vehicleTypeEntity;
	}

	public VehicleType toDomain() {
		return VehicleType.builder()
			.id(id)
			.vehicleTypesName(vehicleTypesName)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
