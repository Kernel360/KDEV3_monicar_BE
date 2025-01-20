package org.eventhub.presentation;

import lombok.extern.slf4j.Slf4j;

import org.common.dto.CommonResponse;
import org.eventhub.application.port.CycleInfoEventPublisher;
import org.eventhub.common.response.BaseResponse;
import org.eventhub.domain.CycleInfoList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/api/v1/event-hub")
@RequiredArgsConstructor
public class CycleInfoController {
	private final CycleInfoEventPublisher cycleInfoEventPublisher;

	@PostMapping("cycle-info")
	public BaseResponse<CommonResponse> cycleInfo(
		@Valid @RequestBody CycleInfoListRequest request
	) {
		log.info("cycle information request in !");
		CycleInfoList cycleInfoList = request.toDomain();
		cycleInfoEventPublisher.publishEvent(cycleInfoList);

		return BaseResponse.emulatorSuccess(request.mdn());
	}
}
