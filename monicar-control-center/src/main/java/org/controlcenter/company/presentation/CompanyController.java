package org.controlcenter.company.presentation;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.company.application.CompanyService;
import org.controlcenter.company.presentation.dto.CompanyCreateRequest;
import org.controlcenter.company.presentation.dto.SimpleCompanyResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/companies")
public class CompanyController {
	private final CompanyService companyService;

	/**
	 * 업체(회사) 등록 api
	 *
	 * @param companyCreateRequest 업체(회사) 등록 요청 데이터
	 * @return 등록된 업체 데이터를 반환
	 */
	@PostMapping
	public BaseResponse<SimpleCompanyResponse> register(
		@Valid @RequestBody CompanyCreateRequest companyCreateRequest
	) {
		var company = companyService.register(companyCreateRequest.toDomain());
		return BaseResponse.success(SimpleCompanyResponse.from(company));
	}
}
