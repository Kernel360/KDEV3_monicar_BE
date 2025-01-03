package org.controlcenter.notice.infrastructure.jpa;

import org.controlcenter.notice.infrastructure.jpa.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {
}
