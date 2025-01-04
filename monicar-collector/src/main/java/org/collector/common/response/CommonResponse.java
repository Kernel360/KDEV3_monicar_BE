package org.collector.common.response;

import java.util.Map;

import org.collector.common.exception.CustomException;

import jakarta.annotation.Nullable;

public record CommonResponse<T>(
	String rstCd,
	Object rstMsg,
	String mdn
) {
	public static <T> CommonResponse<T> ok(final long mdn) {
		return new CommonResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), Long.toString(mdn));
	}

	public static <T> CommonResponse<T> fail(final CustomException e, @Nullable final String mdn) {
		return new CommonResponse<>(e.getResponseCode().getCode(), e.getResponseCode().getMessage(), mdn);
	}

	public static <T> CommonResponse<T> validationError(Map<String, String> validationErrors, @Nullable final String mdn) {
		return new CommonResponse<>(ResponseCode.REQUIRED_PARAMETER_ERROR.getCode(), validationErrors, mdn);
	}
}
