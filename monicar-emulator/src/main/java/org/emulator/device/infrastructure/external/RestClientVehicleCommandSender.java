package org.emulator.device.infrastructure.external;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.VehicleCommandSender;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.util.HeaderName;
import org.emulator.device.infrastructure.util.HeaderUtils;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@RequiredArgsConstructor
@Component
public class RestClientVehicleCommandSender implements VehicleCommandSender {
	private final RestClientService restClientService;

	public CommonResponse sendOnCommand(OnInfo onInfo) {
		RestClient restClient = restClientService.getRestClient(UrlPathEnum.CONTROL_CENTER);

		// body: onInfo - onCommand 매핑
		OnCommand onCommand = OnCommand.from(onInfo);
		Map<String, String> headers = HeaderUtils.additionalHeaders(HeaderName.TIMESTAMP, HeaderName.TUID);

		return restClientService.post(
			restClient,
			"key-on",
			onCommand,
			headers
		);
	}

	@Override
	public CommonResponse sendCycleCommand(List<CycleInfo> cycleInfos) {
		return null;
	}
}
