DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS alarm;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS vehicle_information;
DROP TABLE IF EXISTS vehicle_types;
DROP TABLE IF EXISTS vehicle_event;
DROP TABLE IF EXISTS cycle_info;
DROP TABLE IF EXISTS driving_history;

CREATE TABLE company
(
    `company_id`                   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '업체 PK',
    `company_name`                 VARCHAR(100) NOT NULL COMMENT '상호명',
    `business_registration_number` VARCHAR(100) NOT NULL COMMENT '사업자 등록번호',
    `created_at`                   TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`                   TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`                   TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='업체';

CREATE TABLE department
(
    `department_id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '부서 PK',
    `company_id`      BIGINT       NOT NULL COMMENT '업체 PK',
    `department_name` VARCHAR(255) NOT NULL COMMENT '부서명',
    `created_at`      TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`      TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`      TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='부서';

CREATE TABLE manager
(
    `manager_id`      VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '담당자 PK',
    `department_id`   BIGINT       NOT NULL COMMENT '부서 PK',
    `email`           VARCHAR(255) NOT NULL COMMENT '이메일' UNIQUE,
    `login_id`        VARCHAR(255) NOT NULL COMMENT '아이디' UNIQUE,
    `password`        VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `nickname`        VARCHAR(255) NOT NULL COMMENT '닉네임',
    `role`            VARCHAR(255) NOT NULL COMMENT '권한',
    `last_login_at`   TIMESTAMP    NULL COMMENT '마지막 로그인 시간',
    `created_at`      TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`      TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`      TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='담당자';

CREATE TABLE alarm
(
    `alarm_id`    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '알림 PK',
    `manager_id`  BIGINT       NOT NULL COMMENT '담당자 PK',
    `description` TEXT         NOT NULL COMMENT '알림 내용',
    `status`      VARCHAR(100) NOT NULL COMMENT '알림 상태',
    `created_at`  TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`  TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`  TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='알림';

CREATE TABLE notice
(
    `notice_id`  BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '공지사항 PK',
    `title`      VARCHAR(100) NOT NULL COMMENT '제목',
    `content`    TEXT         NOT NULL COMMENT '내용',
    `image_url`  VARCHAR(255) NULL COMMENT '이미지 URL',
    `created_at` TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at` TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at` TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='공지사항';

CREATE TABLE vehicle_information
(
    `vehicle_id`      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '차량 PK',
    `company_id`      BIGINT       NOT NULL COMMENT '업체 PK',
    `vehicle_type_id` BIGINT       NOT NULL COMMENT '차종 PK',
    `vehicle_number`  VARCHAR(100) NOT NULL COMMENT '차량 표지판 번호',
    `mdn`             BIGINT       NOT NULL COMMENT '차량번호',
    `tid`             VARCHAR(100) NOT NULL COMMENT '터미널 아이디',
    `mid`             INT          NOT NULL COMMENT '제조사 아이디',
    `pv`              INT          NOT NULL COMMENT '패킷버전',
    `did`             INT          NOT NULL COMMENT '디바이스 아이디',
    `driving_days`    INT          NOT NULL DEFAULT 0 COMMENT '운행 일수',
    `sum`             BIGINT       NOT NULL COMMENT '누적 주행 거리',
    `lat`             INT          NULL COMMENT '위도 * 1000000 한값(소수점 6자리)',
    `lng`             INT          NULL COMMENT '경도 * 1000000 한값(소수점 6자리)',
    `status`          VARCHAR(100) NOT NULL DEFAULT 'NOT_REGISTERED' COMMENT '차량 상태',
    `delivery_date`   DATE         NOT NULL COMMENT '출고일자',
    `created_at`      TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`      TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`      TIMESTAMP    NULL COMMENT '테이블 삭제 시간',
    constraint mdn
        unique (mdn)
) ENGINE = InnoDB COMMENT ='차량정보';

CREATE TABLE vehicle_types
(
    `vehicle_types_id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '차종 PK',
    `vehicle_types_name` VARCHAR(255) NOT NULL COMMENT '차종명',
    `created_at`         TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`         TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`         TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='차종';

CREATE TABLE vehicle_event
(
    `vehicle_event_id` BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '차량 이벤트 PK',
    `vehicle_id`       BIGINT       NOT NULL COMMENT '차량 PK',
    `type`             VARCHAR(100) NOT NULL COMMENT '이벤트 타입',
    `event_at`         TIMESTAMP    NOT NULL COMMENT '발생 시간',
    `created_at`       TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간'
) ENGINE = InnoDB COMMENT ='차량 이벤트';

CREATE TABLE cycle_info
(
    `cycle_info_id` BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '주기정보 PK',
    `vehicle_id`    BIGINT       NOT NULL COMMENT '차량 PK',
    `interval_at`   TIMESTAMP    NOT NULL COMMENT '발생시간',
    `status`        VARCHAR(100) NOT NULL COMMENT 'GPS 상태',
    `lat`           INT          NOT NULL COMMENT '위도 * 1000000 한값(소수점 6자리)',
    `lng`           INT          NOT NULL COMMENT '경도 * 1000000 한값(소수점 6자리)',
    `ang`           INT          NOT NULL COMMENT '방향 - 범위 : 0 ~ 365',
    `spd`           INT          NOT NULL COMMENT '속도 - 범위 : 0~ 255(단위: km/h)',
    `created_at`    TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간'
) ENGINE = InnoDB COMMENT ='주기 정보';

CREATE TABLE driving_history
(
    `driving_history_id` BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '운행내역 PK',
    `vehicle_id`         BIGINT       NOT NULL COMMENT '차량 PK',
    `department_id`      BIGINT       NULL COMMENT '부서 PK',
    `driver_email`       VARCHAR(255) NOT NULL COMMENT '운전자 이메일',
    `initial_odometer`   DOUBLE       NOT NULL COMMENT '주행 전 계기판의 거리(km)',
    `final_odometer`     DOUBLE       NOT NULL COMMENT '주행 후 계기판의 거리(km)',
    `driving_distance`   DOUBLE       NOT NULL COMMENT '주행 거리(km)',
    `use_purpose`        VARCHAR(100) NOT NULL COMMENT '사용목적',
    `start_time`         TIMESTAMP    NOT NULL COMMENT 'start_time',
    `end_time`           TIMESTAMP    NOT NULL COMMENT 'end_time',
    `created_at`         TIMESTAMP    NOT NULL COMMENT '테이블 생성 시간',
    `updated_at`         TIMESTAMP    NOT NULL COMMENT '테이블 수정 시간',
    `deleted_at`         TIMESTAMP    NULL COMMENT '테이블 삭제 시간'
) ENGINE = InnoDB COMMENT ='운행 내역';