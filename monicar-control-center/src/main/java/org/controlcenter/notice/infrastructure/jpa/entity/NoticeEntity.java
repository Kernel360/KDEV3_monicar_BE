package org.controlcenter.notice.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import org.controlcenter.notice.domain.Notice;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "notice")
public class NoticeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long id;

	private String title;

	private String content;

	private String imageUrl;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static NoticeEntity from(Notice notice) {
		NoticeEntity noticeEntity = new NoticeEntity();
		noticeEntity.id = notice.getId();
		noticeEntity.title = notice.getTitle();
		noticeEntity.content = notice.getContent();
		noticeEntity.imageUrl = notice.getImageUrl();
		noticeEntity.createdAt = notice.getCreatedAt();
		noticeEntity.updatedAt = notice.getUpdatedAt();
		noticeEntity.deletedAt = notice.getDeletedAt();
		return noticeEntity;
	}

	public Notice toDomain() {
		return Notice.builder()
			.id(id)
			.title(title)
			.content(content)
			.imageUrl(imageUrl)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
