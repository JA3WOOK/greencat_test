CREATE TABLE IF NOT EXISTS `calendar`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) DEFAULT NULL,
    `date`        date          DEFAULT NULL,
    `description` varchar(1000) DEFAULT NULL,
    `end_time`    time(6)       DEFAULT NULL,
    `address`     varchar(255)  DEFAULT NULL,
    `city`        varchar(255)  DEFAULT NULL,
    `country`     varchar(255)  DEFAULT NULL,
    `state`       varchar(255)  DEFAULT NULL,
    `venue`       varchar(255)  DEFAULT NULL,
    `start_time`  time(6)       DEFAULT NULL,
    `status`      enum('CANCELLED','COMPLETED','IN_PROGRESS','SCHEDULED') DEFAULT NULL,
    `title`       varchar(255)  DEFAULT NULL,
    `updated_at`  datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `calendar_shared_emails`
(
    `event_id` bigint(20) NOT NULL,
    `email`    varchar(255) DEFAULT NULL,
    KEY        `event_id_idx` (`event_id`),
    CONSTRAINT `fk_event_id`
        FOREIGN KEY (`event_id`) REFERENCES `calendar` (`id`)
            ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `todo`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `completed`   bit(1) NOT NULL,
    `created_at`  datetime(6) NOT NULL,
    `description` varchar(1000) DEFAULT NULL,
    `title`       varchar(255)  DEFAULT NULL,
    `updated_at`  datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
