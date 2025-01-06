package org.collector.common.exception;

import org.collector.common.response.ResponseCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ResponseCode responseCode;

	public CustomException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.responseCode = responseCode;
	}
}
