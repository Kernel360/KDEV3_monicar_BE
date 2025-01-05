package org.emulator.device.infrastructure.external;

import java.io.InputStream;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Service
public class RestClientService {
	private final Map<UrlPathEnum, RestClient> restClients;
	private final ObjectMapper objectMapper;

	public RestClient getRestClient(UrlPathEnum path) {
		return restClients.get(path);
	}

	public CommonResponse post(
		RestClient restClient,
		String endPoint,
		Object body,
		Map<String, String> headers
	) {
		return restClient.post()
			.uri(uriBuilder -> uriBuilder.path(endPoint).build())
			.headers(header -> headers.forEach(header::add))
			.body(body)
			.exchange((request, response) -> {
				InputStream responseBody = response.getBody();
				return objectMapper.readValue(responseBody, CommonResponse.class);
			});
	}
}