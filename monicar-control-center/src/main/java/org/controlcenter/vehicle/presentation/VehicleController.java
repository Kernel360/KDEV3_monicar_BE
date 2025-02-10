package org.controlcenter.vehicle.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.response.code.SuccessCode;
import org.controlcenter.vehicle.application.ClusterService;
import org.controlcenter.vehicle.application.VehicleClusteringService;
import org.controlcenter.vehicle.application.VehicleService;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.ClusterDetail;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.VehicleRegister;
import org.controlcenter.vehicle.domain.VehicleStatus;
import org.controlcenter.vehicle.domain.cluster.ClusterCreateCommand;
import org.controlcenter.vehicle.domain.cluster.GeoClustering;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinateDetails;
import org.controlcenter.vehicle.infrastructure.VehicleQueryRepository;
import org.controlcenter.vehicle.presentation.dto.ClusterResponse;
import org.controlcenter.vehicle.presentation.dto.GeoClusteringResponse;
import org.controlcenter.vehicle.presentation.dto.GeoCoordinateDetailsResponse;
import org.controlcenter.vehicle.presentation.dto.RouteResponse;
import org.controlcenter.vehicle.presentation.dto.RouteResponseWithAng;
import org.controlcenter.vehicle.presentation.dto.VehicleEngineStatusResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleInfoSearchRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleLocationResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleModalResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleRegisterRequest;
import org.controlcenter.vehicle.presentation.dto.VehicleRouteResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleRouteWithAngResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleRouteWithStatusResponse;
import org.controlcenter.vehicle.presentation.swagger.VehicleApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vehicle")
public class VehicleController implements VehicleApi {
	private final VehicleQueryRepository vehicleQueryRepository;
	private final VehicleClusteringService vehicleClusteringService;
	private final VehicleService vehicleService;
	private final ClusterService clusterService;
	private final VehicleRepository vehicleRepository;

	@GetMapping
	public BaseResponse<VehicleInfoResponse> getVehicleInfo(
		@Valid VehicleInfoSearchRequest request
	) {
		var vehicleInfoResponse = vehicleQueryRepository.getVehicleInfo(request);
		return BaseResponse.success(vehicleInfoResponse);
	}

	@PostMapping("/register")
	public BaseResponse<VehicleInformation> register(
		@Valid @RequestBody VehicleRegisterRequest vehicleRegisterRequest
	) {
		VehicleRegister vehicleRegister = vehicleRegisterRequest.toDomain();
		VehicleInformation vehicleInformation = vehicleService.register(vehicleRegister);

		return BaseResponse.success(vehicleInformation);
	}

	@DeleteMapping("/{vehicle-id}")
	public BaseResponse<Void> deleteVehicle(
		@Valid @PathVariable(name = "vehicle-id") Long vehicleId
	) {
		vehicleService.deleteVehicle(vehicleId);

		return BaseResponse.success(SuccessCode.DELETED);
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
		var vehicleCompanyInfo = vehicleQueryRepository.getVehicleCompanyInfo(vehicleId);

		VehicleModalResponse response = VehicleModalResponse.builder()
			.recentVehicleInfo(recentVehicleInfo)
			.recentCycleInfo(recentCycleInfo)
			.todayDrivingHistory(todayDrivingHistory)
			.vehicleCompanyInfo(vehicleCompanyInfo)
			.build();
		return BaseResponse.success(response);
	}

	/**
	 * 차량 번호를 기반으로 최신 정보를 조회하는 API
	 */
	@GetMapping("/search")
	@PreAuthorize("hasRole('ROLE_USER')")
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
	public BaseResponse<VehicleRouteWithAngResponse> getVehicleRoute(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(value = "startTime") LocalDateTime startTime,
		@RequestParam(value = "endTime") LocalDateTime endTime,
		@RequestParam(value = "interval", defaultValue = "60") Integer interval
	) {
		String vehicleNumber = vehicleService.getVehicleNumber(vehicleId);

		List<RouteResponseWithAng> routesResponses = vehicleQueryRepository.getVehicleRouteFrom(vehicleId, startTime,
			endTime,
			interval);

		VehicleRouteWithAngResponse response = VehicleRouteWithAngResponse.builder()
			.vehicleNumber(vehicleNumber)
			.routes(routesResponses)
			.build();
		return BaseResponse.success(response);
	}

	/**
	 *  개별 차량 경로 페이진이션 API
	 */
	@GetMapping("/{vehicle-id}/routes/detail")
	public BaseResponse<PageResponse<VehicleRouteResponse>> getVehicleRouteWithPagination(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(value = "startTime") LocalDateTime startTime,
		@RequestParam(value = "endTime") LocalDateTime endTime,
		@RequestParam(value = "interval", defaultValue = "60") Integer interval,
		@PageableDefault(size = 5) Pageable pageable
	) {
		String vehicleNumber = vehicleService.getVehicleNumber(vehicleId);

		Page<RouteResponse> routePage = vehicleQueryRepository.getVehicleRouteFromWithPagination(
			vehicleId, startTime, endTime, interval, pageable
		);

		VehicleRouteResponse vehicleRouteResponse = VehicleRouteResponse.builder()
			.vehicleNumber(vehicleNumber)
			.routes(routePage.getContent())
			.build();

		PageResponse<VehicleRouteResponse> pageResponse = new PageResponse<>(
			List.of(vehicleRouteResponse), pageable, routePage.getTotalElements()
		);

		return BaseResponse.success(pageResponse);
	}

	/**
	 * (실시간 용) 개별 차량 경로 조회 API
	 */
	@GetMapping("/{vehicle-id}/recent/routes")
	public BaseResponse<VehicleRouteWithStatusResponse> getRecentRoutesByVehicle(
		@PathVariable("vehicle-id") Long vehicleId,
		@RequestParam(value = "currentTime") LocalDateTime currentTime
	) {
		String vehicleNumber = vehicleService.getVehicleNumber(vehicleId);

		List<RouteResponseWithStatus> routesResponses = vehicleQueryRepository.getRecentRoutesByVehicle(vehicleId,
			currentTime);

		VehicleRouteWithStatusResponse response = VehicleRouteWithStatusResponse.builder()
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
	 * 지도 클러스터링 상세 조회 API
	 */
	@GetMapping("/cluster/details")
	public BaseResponse<List<GeoCoordinateDetailsResponse>> clusterCoordinateDetail(
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

		List<GeoCoordinateDetails> GeoCoordinateDetails = vehicleClusteringService.clusterCoordinateDetail(
			ClusterCreateCommand.of(level, neLat, neLng, swLat, swLng),
			companyId
		);

		var response = GeoCoordinateDetails.stream()
			.map(GeoCoordinateDetailsResponse::from)
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

	@GetMapping("/clusters")
	public BaseResponse<List<ClusterResponse>> getClusters(
		@RequestParam("neLat") int neLat,
		@RequestParam("neLng") int neLng,
		@RequestParam("swLat") int swLat,
		@RequestParam("swLng") int swLng,
		@RequestParam("zoomLevel") int zoomLevel,
		@RequestParam(value = "status", defaultValue = "") VehicleStatus status) {

		List<ClusterResponse> clusterResponses = clusterService
			.getClusters(neLat, neLng, swLat, swLng, zoomLevel, status)
			.stream().map(ClusterResponse::from).toList();
		return BaseResponse.success(clusterResponses);
	}

	/**
	 * 운행중인 차량인지 조회
	 */
	@GetMapping("/{vehicle-id}/operaton-status")
	public BaseResponse<Boolean> isVehicleInOperation(
		@PathVariable("vehicle-id") Long vehicleId
	) {
		boolean inOperation = vehicleService.isVehicleInOperation(vehicleId);

		return BaseResponse.success(inOperation);
	}

	@GetMapping("/check")
	public BaseResponse<Void> isExistVehicleNumber(
		@RequestParam("vehicleNumber") String vehicleNumber
	) {
		boolean exists = vehicleRepository.findByVehicleNumber(vehicleNumber).isPresent();
		if (exists) {
			return BaseResponse.fail(ErrorCode.VEHICLE_ALREADY_EXIST);
		} else {
			return BaseResponse.success();
		}
	}

	@GetMapping("/clusters/detail")
	public BaseResponse<List<ClusterDetail>> getClustersDetail(
		@RequestParam("neLat") int neLat,
		@RequestParam("neLng") int neLng,
		@RequestParam("swLat") int swLat,
		@RequestParam("swLng") int swLng,
		@RequestParam(value = "status", defaultValue = "") VehicleStatus status
	) {
		List<ClusterDetail> clusterResponses = clusterService
			.getClustersDetail(neLat, neLng, swLat, swLng, status);

		return BaseResponse.success(clusterResponses);
	}

	@GetMapping("/clusters/{vehicle-id}")
	public BaseResponse<VehicleLocationResponse> getVehicleByVehicleId(
		@Valid @PathVariable(name = "vehicle-id") Long vehicleId
	) {
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(vehicleId);

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
}
