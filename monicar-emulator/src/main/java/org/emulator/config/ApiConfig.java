package org.emulator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class ApiConfig {
	@Value("${api.protocol}")
	private String protocol;
	@Value("${api.host}")
	private String host;
	@Value("${api.port}")
	private String port;
	@Value("${api.server}")
	private String server;

	public String getBaseUrl() {
		return String.format("%s://%s:%s/api/v1/%s/", protocol, host, port, server);
	}
}

