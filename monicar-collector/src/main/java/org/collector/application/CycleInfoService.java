package org.collector.application;

import java.util.List;

import org.collector.domain.CycleInfo;
import org.collector.domain.Vehicle;
import org.collector.infrastructure.repository.CycleInfoRepository;
import org.collector.infrastructure.repository.VehicleRepository;
import org.collector.presentation.dto.CListRequest;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CycleInfoService {
	private final CycleInfoRepository cycleInfoRepository;
	private final VehicleRepository vehicleRepository;

	@Transactional
	public long cycleInfoSave(final CycleInfoRequest request) {
		Vehicle vehicle = vehicleRepository.findByMdn(request.mdn())
				.orElseThrow(IllegalArgumentException::new);

		CycleInfoRequest.from(request);

		vehicleRepository.save(vehicle);

		List<CycleInfo> cycleInfos = request.cList().stream()
			.map(cListRequest -> CListRequest.from(cListRequest, vehicle))
			.toList();

		cycleInfoRepository.saveAll(cycleInfos);
		return vehicle.getMdn();
	}
}
