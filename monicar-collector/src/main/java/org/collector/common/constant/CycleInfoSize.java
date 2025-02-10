package org.collector.common.constant;

import org.collector.common.exception.CustomException;
import org.collector.common.response.ResponseCode;

import lombok.Getter;

@Getter
public enum CycleInfoSize {
	MIN_SIZE(60),
	LAST_INDEX(59);

	private final int size;

	CycleInfoSize(int size) {
		this.size = size;
	}

	public void validate(int size) {
		if (size < this.size) {
			throw new CustomException(ResponseCode.NOT_CYCLE_INFO_SIZE_ERROR);
		}
	}
}
