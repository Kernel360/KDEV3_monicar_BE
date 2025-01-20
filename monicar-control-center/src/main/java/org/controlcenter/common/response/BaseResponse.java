package org.controlcenter.common.response;

import java.util.List;

import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.response.code.SuccessCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

/**
 * API 요청에 대한 응답 표준화 클래스
 *
 * @param <T> 요청의 결과 데이터 타입
 */
@Getter
@Builder
public class BaseResponse<T> {
	private final Boolean isSuccess;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<String> errorMessage;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T result;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer errorCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long timestamp;

	public static <T> BaseResponse<T> success(SuccessCode code, T data) {
		return BaseResponse.<T>builder()
			.isSuccess(true)
			.message(code.getMessage())
			.result(data)
			.build();
	}

	public static <T> BaseResponse<T> success(T data) {
		return BaseResponse.<T>builder()
			.isSuccess(true)
			.message(SuccessCode.OK.getMessage())
			.result(data)
			.build();
	}

	public static <T> BaseResponse<T> success(SuccessCode code) {
		return BaseResponse.<T>builder()
			.isSuccess(true)
			.message(code.getMessage())
			.build();
	}

	public static BaseResponse<Void> success() {
		return success(SuccessCode.OK);
	}

	public static <T> BaseResponse<T> fail(ErrorCode code) {
		return BaseResponse.<T>builder()
			.isSuccess(false)
			.errorMessage(List.of(code.getMessage()))
			.errorCode(code.getCode())
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static <T> BaseResponse<T> fail(List<String> errorMessages) {
		return BaseResponse.<T>builder()
			.isSuccess(false)
			.errorMessage(errorMessages)
			.errorCode(1000)
			.timestamp(System.currentTimeMillis())
			.build();
	}
}
