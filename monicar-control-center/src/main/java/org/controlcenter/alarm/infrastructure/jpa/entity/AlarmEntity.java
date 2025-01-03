package org.controlcenter.alarm.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "alarm")
public class AlarmEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_id")
	private Long id;

	private Long managerId;

	private String description;

	@Enumerated(value = EnumType.STRING)
	private AlarmStatus status;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static AlarmEntity from(Alarm alarm) {
		AlarmEntity alarmEntity = new AlarmEntity();
		alarmEntity.id = alarm.getId();
		alarmEntity.managerId = alarm.getManagerId();
		alarmEntity.description = alarm.getDescription();
		alarmEntity.status = alarm.getStatus();
		alarmEntity.createdAt = alarm.getCreatedAt();
		alarmEntity.updatedAt = alarm.getUpdatedAt();
		alarmEntity.deletedAt = alarm.getDeletedAt();
		return alarmEntity;
	}

	public Alarm toDomain() {
		return Alarm.builder()
			.id(id)
			.managerId(managerId)
			.description(description)
			.status(status)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
