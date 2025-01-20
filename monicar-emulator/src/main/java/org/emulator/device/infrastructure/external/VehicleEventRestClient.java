package org.emulator.device.infrastructure.external;

import java.util.List;

import java.util.Optional;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.VehicleEventHttpClient;
import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class VehicleEventRestClient implements VehicleEventHttpClient {
	private static final String SUCCESSFULLY_ON = "000";
	private final RestClientService restClientService;

	public CommonResponse sendOnEvent(OnInfo onInfo) {
		OnCommand onCommand = OnCommand.from(onInfo);

		return handleResponse(restClientService.post("key-on", onCommand));
	}

	@Override
	public CommonResponse sendCycleInfoEvent(List<CycleInfo> cycleInfo) {
		CycleInfoListCommand cycleInfoListCommand = CycleInfoListCommand.from(cycleInfo);

		return handleResponse(restClientService.post("cycle-info", cycleInfoListCommand));
	}

	private CommonResponse handleResponse(Optional<CommonResponse> optionalResponse) {
		return optionalResponse
			.filter(response -> isSuccess(response.rstCd()))
			.orElseThrow(() -> new BusinessException(ErrorCode.UNSUPPORTED_RESPONSE));
	}

	private boolean isSuccess(String responseCode) {
		return SUCCESSFULLY_ON.equals(responseCode);
	}
}
