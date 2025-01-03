package org.controlcenter.geoinfo.domain;

public enum GpsStatus {
	A("정상"),
	V("비정상"),
	O("미장착");

	private String description;

	GpsStatus(String description) {
		this.description = description;
	}
}
