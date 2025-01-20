package org.eventhub.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
	OK("요청 성공");

	private final String message;
}
