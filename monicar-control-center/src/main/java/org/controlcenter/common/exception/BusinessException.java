package org.controlcenter.common.exception;

import org.controlcenter.common.response.code.ErrorCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	/**
	 * 예외 상황을 설명하는 BusinessException 인스턴스. 예외의 구체적인 코드와 메시지 전달
	 */
	private final ErrorCode errorCode;

	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
