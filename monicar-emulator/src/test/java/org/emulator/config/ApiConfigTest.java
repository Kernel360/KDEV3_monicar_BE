package org.emulator.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
public class ApiConfigTest {

	@Autowired
	private ApiConfig apiConfig;

	@Test
	void testApiConfigValues() {
		assertEquals("http", apiConfig.getProtocol());
		assertEquals("localhost", apiConfig.getHost());
		assertEquals("8082", apiConfig.getPort());
		assertEquals("event-hub", apiConfig.getServer());

		String expectedBaseUrl = "http://localhost:8082/api/v1/event-hub/";
		assertEquals(expectedBaseUrl, apiConfig.getBaseUrl());
	}
}