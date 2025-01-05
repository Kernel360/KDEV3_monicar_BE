package org.controlcenter.vehicle.infrastructure.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;

@Mapper
public interface MyBatisVehicleInfoMapper {

	@Select("""
			select
				vi.vehicle_id as vehicleId,
				vi.vehicle_number as vehicleNumber,
				vi.created_at as firstDataAt,
				(
					select ci.created_at
					from cycle_info ci
					where ci.vehicle_id = vi.vehicle_id
					order by ci.interval_at DESC
					limit 1
				) as lastDataAt
			from vehicle_information vi
			where vi.vehicle_number = #{vehicleNumber}
			  and vi.deleted_at is null;
		""")
	VehicleInfoResponse selectVehicleInfo(@Param("vehicleNumber") String vehicleNumber);
}
