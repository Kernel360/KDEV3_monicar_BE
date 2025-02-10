package org.controlcenter.common.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode API 응답에서 발생할 수 있는 오류 코드와 메시지 enum 각 오류 상황에 대한 HttpStatus, 오류 코드, 설명 메시지
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
	// 공통(Common) 오류
	INVALID_INPUT_VALUE(1001, "잘못된 값을 입력했습니다."),
	METHOD_NOT_ALLOWED(1002, "허용되지 않은 메서드입니다."),
	ENTITY_NOT_FOUND(1003, "엔티티를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(1004, "서버 오류가 발생하였습니다."),
	INVALID_TYPE_VALUE(1005, "잘못된 유형 값을 입력하였습니다."),
	HANDLE_ACCESS_DENIED(1006, "액세스가 거부되었습니다."),
	FORBIDDEN_ACCESS(1007, "비정상적 접근입니다."),
	EMPTY_PATH_VARIABLE(1008, "필수 경로 변수가 누락되었습니다. 요청 경로에 올바른 값을 입력해 주세요."),
	ENTITY_ALREADY_EXIST(1009, "이미 존재하는 엔티티 입니다."),
	NOT_FOUND(1010, "요청한 리소스를 찾을 수 없습니다."),
	INVALID_TYPE_USERID_VALUE(1011, "아이디는 영문자와 숫자만 입력할 수 있습니다."),
	EMAIL_ALREADY_EXIST(1012, "다른 이메일을 입력해주세요."),
	USER_ID_ALREADY_EXIST(1013, "다른 아이디를 입력해주세요."),
	INVALID_DATE_RANGE(1014, "시작 날짜가 종료 날짜보다 이후로 입력하였습니다. 날짜를 확인해주세요."),

	VEHICLE_NOT_FOUND(2001, "해당 차량을 찾을 수 없습니다."),
	VEHICLE_ALREADY_EXIST(2002, "이미 등록되어 있는 차량입니다."),
	VEHICLE_NUMBER_ERROR(2003, "입력된 차량 번호 형식이 올바르지 않습니다. 형식: 숫자 2~3자리 + 한글 1자리 + 숫자 4자리"),
	INVALID_ZOOM_LEVEL(2004, "지도의 줌 레벨은 1~13 사이여야합니다."),

	AUTHORIZATION_DENIED(9000, "권한이 없습니다."),
	EXPIRED_TOKENS(9993, "토큰 만료로 인해 로그아웃 되었습니다. 다시 로그인해 주세요."),
	EXPIRED_REFRESH_TOKEN(9994, "만료된 리프레시 토큰입니다."),
	EXPIRED_ACCESS_TOKEN(9995, "만료된 액세스 토큰입니다."),
	INVALID_REFRESH_TOKEN(9996, "유효하지 않은 리프레시 토큰입니다."),
	INVALID_ACCESS_TOKEN(9997, "유효하지 않은 액세스 토큰입니다."),
	REFRESH_TOKEN_STILL_VALID(9998, "유효한 리프레시 토큰입니다."),
	LOGIN_FAILED(9999, "등록되지 않은 아이디이거나, 아이디 혹은 비밀번호를 잘못 입력하였습니다.");

	private final int code;
	private final String message;
}