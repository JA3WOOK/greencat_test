-- calendar 테이블 더미 데이터
INSERT INTO `calendar` (`created_at`, `date`, `description`, `end_time`, `address`, `city`, `country`, `state`, `venue`,
                        `start_time`, `status`, `title`, `updated_at`)
VALUES (NOW(), '2025-05-01', '첫 번째 이벤트 설명입니다.', '18:00:00', '123 Main St', 'Seoul', 'South Korea', 'Seoul',
        'Convention Center', '15:00:00', 'SCHEDULED', '첫 번째 이벤트', NOW()),
       (NOW(), '2025-05-05', '두 번째 이벤트 설명입니다.', '20:00:00', '456 Side St', 'Busan', 'South Korea', 'Busan',
        'Exhibition Hall', '17:00:00', 'IN_PROGRESS', '두 번째 이벤트', NOW()),
       (NOW(), '2025-06-10', '세 번째 이벤트 설명입니다.', '19:30:00', '789 Center Rd', 'Daegu', 'South Korea', 'Daegu',
        'Event Plaza', '16:00:00', 'COMPLETED', '세 번째 이벤트', NOW()),
       (NOW(), '2025-07-15', '네 번째 이벤트 설명입니다.', '21:00:00', '321 Uptown Ave', 'Incheon', 'South Korea', 'Incheon',
        'Civic Center', '18:30:00', 'CANCELLED', '네 번째 이벤트', NOW()),
       (NOW(), '2025-08-20', '다섯 번째 이벤트 설명입니다.', '17:00:00', '654 Downtown St', 'Gwangju', 'South Korea', 'Gwangju',
        'Cultural Hall', '14:00:00', 'SCHEDULED', '다섯 번째 이벤트', NOW());

-- calendar_shared_emails 테이블 더미 데이터
INSERT INTO `calendar_shared_emails` (`event_id`, `email`)
VALUES (1, 'user1@example.com'),
       (1, 'user2@example.com'),
       (3, 'user3@example.com'),
       (4, 'user4@example.com'),
       (5, 'user5@example.com');

-- todo 테이블 더미 데이터
INSERT INTO `todo` (`completed`, `created_at`, `description`, `title`, `updated_at`)
VALUES (b'0', NOW(), '첫 번째 할 일 설명입니다.', '첫 번째 할 일', NOW()),
       (b'1', NOW(), '두 번째 할 일 설명입니다.', '두 번째 할 일', NOW()),
       (b'0', NOW(), '세 번째 할 일 설명입니다.', '세 번째 할 일', NOW()),
       (b'1', NOW(), '네 번째 할 일 설명입니다.', '네 번째 할 일', NOW()),
       (b'0', NOW(), '다섯 번째 할 일 설명입니다.', '다섯 번째 할 일', NOW());
