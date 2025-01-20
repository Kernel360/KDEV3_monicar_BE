package org.eventhub.domain;

public enum GpsStatus {
	A("정상"),
	V("비정상"),
	O("미장착"),
	P("시동 OFF 시 GPS 정보가 비정상");

	private String description;

	GpsStatus(String description) {
		this.description = description;
	}
}
