package org.controlcenter.vehicle.infrastructure;

import org.controlcenter.geoinfo.infrastructure.jpa.entity.QCycleInfoEntity;
import org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleInformationEntity;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public VehicleInfoResponse getVehicleInfo(VehicleInfoSearchRequest request) {

		QVehicleInformationEntity vehicleInformationEntity = QVehicleInformationEntity.vehicleInformationEntity;
		QCycleInfoEntity cycleInfoEntity = QCycleInfoEntity.cycleInfoEntity;

		JPAQuery<VehicleInfoResponse> query = jpaQueryFactory
			.select(
				Projections.fields(
					VehicleInfoResponse.class,
					vehicleInformationEntity.id.as("vehicleId"),
					vehicleInformationEntity.vehicleNumber.as("vehicleNumber"),
					vehicleInformationEntity.createdAt.as("fistDataAt")
				)
			)
			.from(vehicleInformationEntity)
			.where(
				vehicleInformationEntity.vehicleNumber.eq(request.vehicleNumber()),
				vehicleInformationEntity.deletedAt.isNull()
			)
			.fetchOne();

		return null;
	}
}
