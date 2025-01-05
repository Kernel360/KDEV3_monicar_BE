package org.controlcenter.vehicle.infrastructure;

import org.controlcenter.vehicle.infrastructure.mybatis.MyBatisVehicleInfoMapper;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final MyBatisVehicleInfoMapper myBatisVehicleInfoMapper;

	public VehicleInfoResponse getVehicleInfo(VehicleInfoSearchRequest request) {
		return myBatisVehicleInfoMapper.selectVehicleInfo(request.vehicleNumber());
	}
}
