package org.emulator.device.infrastructure.external;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.VehicleCommandSender;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class RestClientVehicleCommandSender implements VehicleCommandSender {
	private final RestClientService restClientService;

	public CommonResponse sendOnCommand(OnInfo onInfo) {
		OnCommand onCommand = OnCommand.from(onInfo);

		return restClientService.post(
			"key-on",
			onCommand
		);
	}
}
