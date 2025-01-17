package org.controlcenter.vehicle.infrastructure.jpa;

import static org.controlcenter.company.infrastructure.jpa.entity.QCompanyEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QDepartmentEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QManagerEntity.*;
import static org.controlcenter.history.infrastructure.jpa.entity.QDrivingHistoryEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleInformationEntity.*;
import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleTypeEntity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.QBusinessMileageDetails;
import org.controlcenter.vehicle.domain.QDrivingInfo;
import org.controlcenter.vehicle.domain.QDrivingLog;
import org.controlcenter.vehicle.domain.QDrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.QVehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
import org.controlcenter.vehicle.presentation.dto.QDrivingUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DrivingLogJpaRepository implements DrivingLogRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<DrivingLog> findByVehicleNumber(String vehicleNumber, Pageable pageable) {
		List<DrivingLog> content = queryFactory
			.select(new QDrivingLog(
				vehicleInformationEntity.id,
				vehicleInformationEntity.vehicleNumber,
				vehicleTypeEntity.vehicleTypesName,
				vehicleInformationEntity.status
			))
			.from(vehicleInformationEntity)
			.join(vehicleTypeEntity)
			.on(vehicleInformationEntity.vehicleTypeId.eq(vehicleTypeEntity.id))
			.where(
				vehicleInformationEntity.vehicleNumber.contains(vehicleNumber)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(Wildcard.count)
			.from(vehicleInformationEntity)
			.where(vehicleInformationEntity.vehicleNumber.contains(vehicleNumber));

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	public Optional<VehicleHeaderInfo> findVehicleHeaderInfoByVehicleId(Long vehicleId) {
		return Optional.ofNullable(
			queryFactory
				.select(createVehicleHeaderInfoProjection())
				.from(vehicleInformationEntity)
				.join(vehicleTypeEntity).on(vehicleInformationEntity.vehicleTypeId.eq(vehicleTypeEntity.id))
				.join(companyEntity).on(vehicleInformationEntity.companyId.eq(companyEntity.id))
				.where(vehicleInformationEntity.id.eq(vehicleId))
				.fetchOne()
		);
	}

	private QVehicleHeaderInfo createVehicleHeaderInfoProjection() {
		return new QVehicleHeaderInfo(
			vehicleInformationEntity.id,
			vehicleInformationEntity.vehicleNumber,
			vehicleTypeEntity.vehicleTypesName,
			companyEntity.id,
			companyEntity.companyName,
			companyEntity.businessRegistrationNumber
		);
	}

	@Override
	public List<DrivingLogDetailsContent> findDrivingLogsByVehicleIdAndDateRange(
		Long vehicleId, LocalDate startDate, LocalDate endDate) {
		return null;
		// return queryFactory
		// 	.select(createDrivingLogDetailsProjection())
		// 	.from(drivingHistoryEntity)
		// 	.leftJoin(managerEntity).on(drivingHistoryEntity.driverEmail.eq(managerEntity.email))
		// 	.leftJoin(departmentEntity).on(managerEntity.departmentId.eq(departmentEntity.id))
		// 	.where(
		// 		drivingHistoryEntity.vehicleId.eq(vehicleId),
		// 		drivingHistoryEntity.usedAt.between(startDate.atStartOfDay(), endDate.atStartOfDay())
		// 	)
		// 	.fetch();
	}

	private QDrivingLogDetailsContent createDrivingLogDetailsProjection() {
		return null;
		// return new QDrivingLogDetailsContent(
		// 	drivingHistoryEntity.usedAt.as("usageDate"),
		// 	createDrivingUserInfoProjection(),
		// 	createDrivingInfoProjection()
		// );
	}

	private QDrivingUserInfo createDrivingUserInfoProjection() {
		return new QDrivingUserInfo(
			managerEntity.id,
			departmentEntity.departmentName,
			managerEntity.nickname
		);
	}

	private QDrivingInfo createDrivingInfoProjection() {
		// return new QDrivingInfo(
		// 	drivingHistoryEntity.initialOdometer,
		// 	drivingHistoryEntity.finalOdometer,
		// 	drivingHistoryEntity.drivingDistance,
		// 	createBusinessMileageDetailsProjection()
		// );
		return null;
	}

	private QBusinessMileageDetails createBusinessMileageDetailsProjection() {
		return null;
		// return new QBusinessMileageDetails(
		// 	drivingHistoryEntity.businessCommuteDistance,
		// 	drivingHistoryEntity.businessUsageDistance
		// );
	}

}
