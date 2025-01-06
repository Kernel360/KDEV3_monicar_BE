package org.collector.application;

import java.util.List;

import org.collector.domain.CycleInfo;
import org.collector.domain.VehicleInformation;
import org.collector.infrastructure.repository.CycleInfoRepository;
import org.collector.infrastructure.repository.VehicleInformationRepository;
import org.collector.presentation.dto.CListRequest;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CycleInfoService {
	private final CycleInfoRepository cycleInfoRepository;
	private final VehicleInformationRepository vehicleInformationRepository;

	@Transactional
	public long cycleInfoSave(final CycleInfoRequest request) {
		VehicleInformation vehicleInformation = vehicleInformationRepository.findByMdn(request.mdn())
			.orElseThrow(IllegalArgumentException::new);

		CycleInfoRequest.from(request);

		vehicleInformationRepository.save(vehicleInformation);

		List<CycleInfo> cycleInfos = request.cList().stream()
			.map(cListRequest -> CListRequest.from(cListRequest, vehicleInformation))
			.toList();

		cycleInfoRepository.saveAll(cycleInfos);
		return vehicleInformation.getMdn();
	}
}
