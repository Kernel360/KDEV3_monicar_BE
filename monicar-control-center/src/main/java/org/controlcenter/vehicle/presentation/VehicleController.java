package org.controlcenter.vehicle.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.vehicle.application.VehicleClusteringService;
import org.controlcenter.vehicle.application.VehicleEventService;
import org.controlcenter.vehicle.application.VehicleService;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.cluster.ClusterCreateCommand;
import org.controlcenter.vehicle.domain.cluster.GeoClustering;
import org.controlcenter.vehicle.infrastructure.VehicleQueryRepository;
import org.controlcenter.vehicle.presentation.dto.GeoClusteringResponse;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleLocationResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleRouteResponse;
import org.controlcenter.vehicle.presentation.swagger.VehicleApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vehicles")
public class VehicleController implements VehicleApi {
	private final VehicleQueryRepository vehicleQueryRepository;
	private final VehicleEventService vehicleEventService;
	private final VehicleClusteringService vehicleClusteringService;
	private final VehicleService vehicleService;

	@GetMapping
	public BaseResponse<VehicleInfoResponse> getVehicleInfo(
		@Valid VehicleInfoSearchRequest request
	) {
		var vehicleInfoResponse = vehicleQueryRepository.getVehicleInfo(request);
		return BaseResponse.success(vehicleInfoResponse);
	}

	/**
	 * 차량 상세 모달 정보 조회 API
	 */
	@GetMapping("/{vehicle-id}")
	public BaseResponse<VehicleModalResponse> getVehicleInfo(
		@PathVariable(name = "vehicle-id") Long vehicleId
	) {
		var recentVehicleInfo = vehicleQueryRepository.getRecentVehicleInfo(vehicleId);
		var recentCycleInfo = vehicleQueryRepository.getRecentCycleInfo(vehicleId);
		var todayDrivingHistory = vehicleQueryRepository.getTodayDrivingHistory(vehicleId);

		VehicleModalResponse response = VehicleModalResponse.builder()
			.recentVehicleInfo(recentVehicleInfo)
			.recentCycleInfo(recentCycleInfo)
			.todayDrivingHistory(todayDrivingHistory)
			.build();
		return BaseResponse.success(response);
	}

	/**
	 * 차량 번호를 기반으로 최신 정보를 조회하는 API
	 */
	@GetMapping("/search")
	public BaseResponse<VehicleLocationResponse> getVehicleInfo(
		@RequestParam("vehicle-number") String vehicleNumber
	) {
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(vehicleNumber);

		String status = vehicleQueryRepository.getVehicleStatus(vehicleInformation.getId());
		var recentCycleInfo = vehicleQueryRepository.getRecentCycleInfo(vehicleInformation.getId());

		VehicleLocationResponse response = VehicleLocationResponse.builder()
			.vehicleId(vehicleInformation.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.recentCycleInfo(recentCycleInfo)
			.status(status)
			.build();
		return BaseResponse.success(response);
	}

	/**
	 * 개별 차량 경로 조회 API
	 */
	@GetMapping("/{vehicle-id}/routes")
	public BaseResponse<VehicleRouteResponse> getVehicleRoute(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(value = "startTime") LocalDateTime startTime,
		@RequestParam(value = "endTime") LocalDateTime endTime,
		@RequestParam(value = "interval", defaultValue = "60") Integer interval
	) {
		String vehicleNumber = vehicleService.getVehicleNumber(vehicleId);

		List<RouteResponse> routesResponses = vehicleQueryRepository.getVehicleRouteFrom(vehicleId, startTime, endTime,
			interval);

		VehicleRouteResponse response = VehicleRouteResponse.builder()
			.vehicleNumber(vehicleNumber)
			.routes(routesResponses)
			.build();
		return BaseResponse.success(response);
	}

	/**
	 * 지도 클러스터링 조회 API
	 */
	@GetMapping("/cluster")
	public BaseResponse<List<GeoClusteringResponse>> clusterCoordinate(
		@RequestParam(value = "level") Integer level,
		@RequestParam(value = "neLat") Integer neLat,
		@RequestParam(value = "neLng") Integer neLng,
		@RequestParam(value = "swLat") Integer swLat,
		@RequestParam(value = "swLng") Integer swLng
	) {

		/**
		 * TODO : 인증 추가하면 헤더값등을 통해 검증해야합니다.(해당 회사의 관리자가 보낸 요청인지)
		 */
		Long companyId = 1L;

		List<GeoClustering> clustering = vehicleClusteringService.clusterCoordinate(
			ClusterCreateCommand.of(level, neLat, neLng, swLat, swLng),
			companyId
		);

		List<GeoClusteringResponse> response = clustering.stream()
			.map(GeoClusteringResponse::from)
			.toList();
		return BaseResponse.success(response);
	}

	/**
	 * 시동 여부에 따른 전체 차량 수 조회 API
	 * - 전체, 시동 ON, 시동 OFF
	 */
	@GetMapping("/status")
	public BaseResponse<VehicleEngineStatusResponse> getVehicleEngineStatus() {

		/**
		 * TODO : 인증 추가하면 헤더값등을 통해 검증해야합니다.(해당 회사의 관리자가 보낸 요청인지)
		 */
		Long companyId = 1L;

		VehicleEngineStatusResponse engineStatusResponse = vehicleQueryRepository.getVehicleEngineStatus(companyId);

		VehicleEngineStatusResponse response = VehicleEngineStatusResponse.builder()
			.allVehicles(engineStatusResponse.allVehicles())
			.engineOnVehicles(engineStatusResponse.engineOnVehicles())
			.engineOffVehicles(engineStatusResponse.engineOffVehicles())
			.build();
		return BaseResponse.success(response);
	}
}
