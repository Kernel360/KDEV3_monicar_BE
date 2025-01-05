package org.emulator.device.infrastructure.external;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.common.dto.CommonResponse;
import org.emulator.config.JacksonConfig;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.emulator.device.infrastructure.util.HeaderName;
import org.emulator.device.infrastructure.util.HeaderUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@DisplayName("RestClientService 요청 테스트")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestClientFactory.class, JacksonConfig.class})
class RestClientServiceTest {

	@Autowired
	private RestClientService restClientService;
	@Autowired
	private ObjectMapper mapper;

	private ClientAndServer mockServer;


	@BeforeEach
	public void setUp() {
		mockServer = ClientAndServer.startClientAndServer(8081);
	}

	@AfterEach
	public void tearDown() {
		mockServer.stop();
	}

	@DisplayName("post 성공 테스트")
	@Test
	void postKeyOn() throws Exception {
		//given
		RestClient restClient = restClientService.getRestClient(UrlPathEnum.CONTROL_CENTER);
		OnCommand command = OnCommand.from(OnInfo.create(LocalDateTime.now(), GpsStatus.A, 20.111111, 30.111111, 5000));
		CommonResponse expected = new CommonResponse("000", "Success", "01234567890");

		mockServer.when(
			request()
				.withMethod("POST")
				.withPath("/api/v1/control-center/key-on")
				.withBody(mapper.writeValueAsString(command)))
			.respond(
				response()
					.withStatusCode(200)
					.withBody(mapper.writeValueAsString(expected))
			);

		//when
		CommonResponse result = restClientService.post(
			restClient,
			"key-on",
			command,
			HeaderUtils.additionalHeaders(HeaderName.TUID)
		);

		//then
		assertEquals(expected, result);
	}
}