package org.eventhub.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.eventhub.application.port.VehicleRepository;
import org.eventhub.domain.VehicleInformation;
import org.eventhub.domain.UpdateTotalDistance;
import org.eventhub.domain.VehicleStatus;
import org.eventhub.infrastructure.repository.jpa.entity.QVehicleInformationEntity;
import org.eventhub.infrastructure.repository.jpa.entity.VehicleInformationEntity;
import org.eventhub.infrastructure.repository.jpa.VehicleInformationJpaRepository;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleInformationRepositoryAdapter implements VehicleRepository {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<VehicleInformation> findById(Long vehicleId) {
		return vehicleInformationJpaRepository.findById(vehicleId)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public Optional<VehicleInformation> findByMdn(Long mdn) {
		return vehicleInformationJpaRepository.findByMdn(mdn)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public VehicleStatus updateVehicleStatus(Long vehicleId, VehicleStatus vehicleStatus) {
		QVehicleInformationEntity vehicleInfo = QVehicleInformationEntity.vehicleInformationEntity;

		jpaQueryFactory.update(vehicleInfo)
			.set(vehicleInfo.status, vehicleStatus)
			.where(vehicleInfo.id.eq(vehicleId))
			.execute();

		return jpaQueryFactory.select(vehicleInfo.status)
			.from(vehicleInfo)
			.where(vehicleInfo.id.eq(vehicleId))
			.fetchOne();
	}

	@Override
	public Long updateTotalDistance(UpdateTotalDistance updateTotalDistanceDto) {
		QVehicleInformationEntity vehicleInfo = QVehicleInformationEntity.vehicleInformationEntity;

		jpaQueryFactory.update(vehicleInfo)
			.set(vehicleInfo.sum, vehicleInfo.sum.add(updateTotalDistanceDto.additionalDistance()))
			.where(vehicleInfo.id.eq(updateTotalDistanceDto.vehicleId()))
			.execute();

		return jpaQueryFactory.select(vehicleInfo.sum)
			.from(vehicleInfo)
			.where(vehicleInfo.id.eq(updateTotalDistanceDto.vehicleId()))
			.fetchOne();
	}

	@Override
	public void updateDrivingDaysAll() {
		QVehicleInformationEntity vehicleInfo = QVehicleInformationEntity.vehicleInformationEntity;

		DateTemplate<Integer> daysDiff = Expressions.dateTemplate(
			Integer.class,
			"DATEDIFF({0}, {1})",
			LocalDateTime.now(),
			vehicleInfo.deliveryDate
		);

		jpaQueryFactory.update(vehicleInfo)
			.set(vehicleInfo.drivingDays, daysDiff)
			.execute();
	}
}
