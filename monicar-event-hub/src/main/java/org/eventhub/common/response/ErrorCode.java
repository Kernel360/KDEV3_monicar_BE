package org.eventhub.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_INPUT_VALUE(1001, "잘못된 값을 입력했습니다."),
	METHOD_NOT_ALLOWED(1002, "허용되지 않은 메서드입니다."),
	ENTITY_NOT_FOUND(1003, "엔티티를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(1004, "서버 오류가 발생하였습니다."),
	INVALID_TYPE_VALUE(1005, "잘못된 유형 값을 입력하였습니다."),
	HANDLE_ACCESS_DENIED(1006, "액세스가 거부되었습니다."),
	FORBIDDEN_ACCESS(1007, "비정상적 접근입니다."),
	EMPTY_PATH_VARIABLE(1008, "필수 경로 변수가 누락되었습니다. 요청 경로에 올바른 값을 입력해 주세요."),
	ENTITY_ALREADY_EXIST(1009, "이미 존재하는 엔티티 입니다."),


	UNSUPPORTED_HEADER(1010, "지원되지 않는 헤더가 입력되었습니다"),
	ILLEGAL_UTILITY_CLASS_ACCESS(1011, "유틸리티 클래스에 대한 잘못된 접근입니다");

	private final int code;
	private final String message;
}

