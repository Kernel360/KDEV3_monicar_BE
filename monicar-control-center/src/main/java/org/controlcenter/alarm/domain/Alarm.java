package org.controlcenter.alarm.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Alarm {
	private Long id;
	private Long managerId;
	private String description;
	private AlarmStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
