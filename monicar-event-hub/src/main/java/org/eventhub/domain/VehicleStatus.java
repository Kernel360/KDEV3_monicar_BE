package org.eventhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleStatus {
	NOT_REGISTERED("미관제"),
	NOT_DRIVEN("미운행"),
	IN_OPERATION("운행중");

	private final String label;
}
