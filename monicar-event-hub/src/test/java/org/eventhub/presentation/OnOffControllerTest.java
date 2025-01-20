package org.eventhub.presentation;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import org.eventhub.application.VehicleEventService;
import org.eventhub.application.VehicleService;
import org.eventhub.domain.GpsStatus;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventType;
import org.eventhub.domain.VehicleInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("VehicleController 테스트")
@WebMvcTest(OnOffController.class)
class OnOffControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private VehicleService vehicleService;
	@MockBean
	private VehicleEventService vehicleEventService;

	@DisplayName("on 요청 success 테스트")
	@Test
	void keyOn() throws Exception {
		long vehicleInformationId = 1L;
		long vehicleEventId = 2L;
		long mdn = 1234567890L;
		int sum = 999;
		when(vehicleService.getVehicleInformation(mdn))
			.thenReturn(VehicleInformation.builder()
				.id(vehicleInformationId)
				.sum(sum)
				.build()
			);

		when(vehicleEventService.getRecentVehicleEvent(vehicleInformationId))
			.thenReturn(VehicleEvent.builder()
				.id(vehicleEventId)
				.type(VehicleEventType.OFF)
				.build()
			);

		String json = new ObjectMapper().writeValueAsString(new KeyOnRequest(mdn, "1111", 11, 1, 111, "20250101080808", "20250101080808", GpsStatus.P, BigDecimal.valueOf(20203030), BigDecimal.valueOf(20203030), 2, 2, 999));
		var result = mockMvc.perform(post("/api/v1/event-hub/key-on")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json)
		);


		result.andDo(print());
		result.andExpect(status().isOk());
	}

	@DisplayName("on 요청 fail 테스트-이미 on인 상태일 때")
	@Test
	void keyOn_fail() throws Exception {
		long vehicleInformationId = 1L;
		long vehicleEventId = 2L;
		long mdn = 1234567890L;
		int sum = 999;
		when(vehicleService.getVehicleInformation(mdn))
			.thenReturn(VehicleInformation.builder()
				.id(vehicleInformationId)
				.sum(sum)
				.build()
			);

		when(vehicleEventService.getRecentVehicleEvent(vehicleInformationId))
			.thenReturn(VehicleEvent.builder()
				.id(vehicleEventId)
				.type(VehicleEventType.ON)
				.build()
			);

		String json = new ObjectMapper().writeValueAsString(new KeyOnRequest(mdn, "1111", 11, 1, 111, "20250101080808", "20250101080808", GpsStatus.P, BigDecimal.valueOf(20203030), BigDecimal.valueOf(20203030), 2, 2, 999));
		var result = mockMvc.perform(post("/api/v1/event-hub/key-on")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json)
		);


		result.andDo(print());
		result.andExpect(content().string(containsString("This is the wrong approach.")));
	}
}