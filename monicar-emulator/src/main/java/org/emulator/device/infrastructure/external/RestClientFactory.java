package org.emulator.device.infrastructure.external;

import org.emulator.config.ApiConfig;
import org.emulator.device.infrastructure.util.HeaderUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RestClientFactory {
	private final ApiConfig apiConfig;

	@Bean
	public RestClient restClient() {
		return RestClient.builder()
			.baseUrl(apiConfig.getBaseUrl())
			.defaultHeaders(HeaderUtils.defaultHeaders())
			.build();
	}

	@Bean
	public RestClientService restClientService(RestClient restClient) {
		return new RestClientService(restClient, new ObjectMapper());
	}
}
