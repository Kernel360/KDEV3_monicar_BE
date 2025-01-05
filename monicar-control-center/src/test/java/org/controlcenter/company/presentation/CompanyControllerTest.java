package org.controlcenter.company.presentation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.controlcenter.company.application.CompanyService;
import org.controlcenter.company.domain.Company;
import org.controlcenter.company.domain.CompanyCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("[controller 단위 테스트] CompanyController")
@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CompanyService companyService;

	@DisplayName("업체(회사)를 등록할 수 있다.")
	@Test
	void register() throws Exception {
		// given
		CompanyCreate companyCreate = CompanyCreate.builder()
			.companyName("company")
			.businessRegistrationNumber("A001")
			.build();

		// stubbing
		Company company = Company.builder()
			.id(1L)
			.companyName("company")
			.businessRegistrationNumber("A001")
			.createdAt(LocalDateTime.of(2024, 12, 1, 0, 0, 0))
			.updatedAt(LocalDateTime.of(2024, 12, 1, 0, 0, 0))
			.deletedAt(null)
			.build();

		when(companyService.register(any(CompanyCreate.class)))
			.thenReturn(company);

		// when
		var result = mockMvc.perform(post("/v1/companies")
			.content(objectMapper.writeValueAsString(companyCreate))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result.andDo(print());
		result.andExpect(status().isOk());
	}

	static Stream<Arguments> nullTestData() {
		return Stream.of(
			Arguments.of(null, "A001"),
			Arguments.of("company", null)
		);
	}

	@DisplayName("쿠폰 등록 요청 시 필드는 null 일 수 없다.")
	@ParameterizedTest(name = "요청 필드 : {0}, {1}")
	@MethodSource("nullTestData")
	void registerCompanyWithNullRequestField(String companyName, String businessRegistrationNumber) throws Exception {
		// given
		CompanyCreate companyCreate = CompanyCreate.builder()
			.companyName(companyName)
			.businessRegistrationNumber(businessRegistrationNumber)
			.build();

		// when
		var result = mockMvc.perform(post("/v1/companies")
			.content(objectMapper.writeValueAsString(companyCreate))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.isSuccess").value(false));
		result.andExpect(jsonPath("$.errorCode").value(1000));
	}

	static Stream<Arguments> emptyTestData() {
		return Stream.of(
			Arguments.of("", "A001"),
			Arguments.of("  ", "A001"),
			Arguments.of("company", ""),
			Arguments.of("company", "  ")
		);
	}

	@DisplayName("쿠폰 등록 요청 시 필드는 empty 일 수 없다.")
	@ParameterizedTest(name = "요청 필드 : {0}, {1}")
	@MethodSource("emptyTestData")
	void registerCompanyWithEmptyRequestField(String companyName, String businessRegistrationNumber) throws Exception {
		// given
		CompanyCreate companyCreate = CompanyCreate.builder()
			.companyName(companyName)
			.businessRegistrationNumber(businessRegistrationNumber)
			.build();

		// when
		var result = mockMvc.perform(post("/v1/companies")
			.content(objectMapper.writeValueAsString(companyCreate))
			.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.isSuccess").value(false));
		result.andExpect(jsonPath("$.errorCode").value(1000));
	}
}