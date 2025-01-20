package org.emulator.device.infrastructure.external;

import java.io.InputStream;
import java.util.Map;

import org.common.dto.CommonResponse;
import org.emulator.device.infrastructure.util.HeaderName;
import org.emulator.device.infrastructure.util.HeaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RestClientService {
	private final RestClient restClient;
	private final ObjectMapper objectMapper;

	public CommonResponse post(
		String endPoint,
		Object body
	) {
		Map<String, String> headers = HeaderUtils.additionalHeaders(HeaderName.TIMESTAMP, HeaderName.TUID);

		return restClient.post()
			.uri(uriBuilder -> uriBuilder.path(endPoint).build())
			.headers(header -> headers.forEach(header::add))
			.body(body)
			.exchange((request, response) -> {
				InputStream responseBody = response.getBody();
				JsonNode rootNode = objectMapper.readTree(responseBody);
				JsonNode resultsNode = rootNode.get("results");
				return objectMapper.treeToValue(resultsNode, CommonResponse.class);
			});
	}
}