package org.collector.presentation.dto;

import java.util.List;

import org.collector.common.annotation.MatchCycleSize;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@MatchCycleSize
public record CycleInfoRequest(
	@NotNull(message = "차량 번호는 필수 입력값입니다.")
	Long mdn,

	@NotBlank(message = "터미널 ID는 필수 입력값입니다.")
	String tid,

	@NotNull(message = "제조사 ID는 필수 입력값입니다.")
	Integer mid,

	@NotNull(message = "패킷 버전은 필수 입력값입니다.")
	@Range(min = 0, max = 65535, message = "패킷버전은 0 ~ 65535 사이여야 합니다.")
	Integer pv,

	@NotNull(message = "디바이스 ID는 필수 입력값입니다.")
	Integer did,

	@NotNull(message = "주기 정보 개수는 필수 입력값입니다.")
	Integer cCnt,

	@Valid
	List<CListRequest> cList
) {
}
