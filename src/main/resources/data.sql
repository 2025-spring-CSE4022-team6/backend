
TRUNCATE TABLE app_user, place, review, review_tag RESTART IDENTITY CASCADE;
-- =================================================================
-- DUMMY DATA INSERT
-- =================================================================

-- 1. 사용자 더미 데이터 (10명)

INSERT INTO app_user (email, nickname, password, profile_path) VALUES
('user1@test.com', '맛잘알', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile1.png'),
('user2@test.com', '서강대미식가', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile2.png'),
('user3@test.com', '마포구주민', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile3.png'),
('user4@test.com', '우정원지박령', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile4.png'),
('user5@test.com', '데이트장인', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile5.png'),
('user6@test.com', '신촌토박이', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile6.png'),
('user7@test.com', '프로회식러', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile7.png'),
('user8@test.com', '리뷰왕김리뷰', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile8.png'),
('user9@test.com', '돈까스제왕', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile9.png'),
('user10@test.com', '서강이', '$2a$10$DowJonesIndustrial/A9wG5Jp8O8CzFqY6K8mJjYwRfY8B8JH5J1Y', '/images/profile10.png');
-- 2. 음식점 더미 데이터 (20곳)
-- score와 total_reviews는 리뷰 데이터 생성 후 마지막에 UPDATE
INSERT INTO place (name, location, latitude, longitude, cuisine, score, total_reviews) VALUES
('고주파', '서울특별시 마포구 광성로6길 28', 37.54865, 126.93880, '요리주점', 0, 0),
('홍등롱', '서울 마포구 광성로4길 11-5', 37.54949, 126.93774, '요리주점', 0, 0),
('열려라 맥주창고', '서울 마포구 백범로4길 12', 37.55289, 126.93643, '맥주호프', 0, 0),
('서강포차', '서울특별시 마포구 백범로 40-1', 37.55108, 126.93750, '요리주점', 0, 0),
('옹고집', '서울 마포구 고산길 16 지층', 37.55348, 126.93811, '한식', 0, 0),
('낭만오지', '서울 마포구 고산길 17', 37.55366, 126.93807, '맥주호프', 0, 0),
('투다리', '서울 마포구 고산길 16', 37.55349, 126.93812, '이자카야', 0, 0),
('마니마니톡톡', '서울 마포구 광성로4길 21-9', 37.54916, 126.93826, '요리주점', 0, 0),
('카스타운', '서울 마포구 고산길 12', 37.55339, 126.93793, '맥주호프', 0, 0),
('타일', '서울 마포구 광성로4길 11-8 1층', 37.54937, 126.93786, '이자카야', 0, 0),
('주담', '서울 마포구 대흥로 78-1 1층', 37.54702, 126.94147, '한식', 0, 0),
('한신우동', '서울 마포구 광성로6길 10 1층', 37.54931, 126.93815, '요리주점', 0, 0),
('가마치통닭', '서울 마포구 광성로 41-1 D동 1층', 37.54997, 126.93755, '치킨', 0, 0),
('정밥', '서울 마포구 광성로6안길 8', 37.54890, 126.93826, '한식', 0, 0),
('기찻길', '서울 마포구 광성로6길 26 기찻길', 37.54871, 126.93871, '이자카야', 0, 0),
('마포야시장', '서울 마포구 백범로 82 상가 107호', 37.54757, 126.94059, '요리주점', 0, 0),
('바른치킨', '서울 마포구 광성로6길 16 1층', 37.54903, 126.93848, '치킨', 0, 0),
('모로미쿠시', '서울 마포구 대흥로 79 1층', 37.54748, 126.94124, '이자카야', 0, 0),
('엔티도트', '서울 마포구 대흥로 80-15 2층', 37.54702, 126.94207, '칵테일바', 0, 0),
('철길부산집', '서울 마포구 광성로6길 34 1층', 37.54836, 126.93897, '이자카야', 0, 0);


-- =================================================================
-- 3. 리뷰 및 리뷰 태그 더미 데이터

-- ===== Place: 고주파 (ID: 1) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(1, 1, '분위기 맛집 인정', '조용하고 대화 나누기 좋은 분위기였어요. 데이트 장소로 추천합니다.', 4.5),
(2, 1, '안주가 예술이에요', '요리주점답게 안주 퀄리티가 정말 높네요. 다른 메뉴도 궁금해져요.', 5.0),
(6, 1, '단체 회식 잘 마쳤습니다', '사장님이 친절하셔서 단체 예약도 수월했고 다들 만족했습니다.', 4.0),
(8, 1, '가성비는 쏘쏘', '맛은 있지만 가격이 저렴한 편은 아니에요. 그래도 분위기 값이라 생각합니다.', 3.5),
(9, 1, '깨끗해서 좋았어요', '술집인데도 화장실이 정말 청결해서 인상 깊었습니다.', 4.5);
-- Tags for reviews 1-5
INSERT INTO review_tag (review_id, tag) VALUES
(1, '분위기'), (1, '대화하기좋음'),
(2, '안주맛집'),
(3, '단체회식'), (3, '직원친절'),
(4, '가성비'),
(5, '화장실굿');

-- ===== Place: 홍등롱 (ID: 2) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(3, 2, '가성비 최고의 중식주점', '가격도 착하고 양도 많아서 푸짐하게 먹고 마셨네요.', 4.5),
(4, 2, '친구랑 시끌벅적하게!', '여럿이서 가서 즐기기 좋은 분위기. 깐풍기가 맛있었어요.', 4.0),
(5, 2, '2차로 오기 딱 좋아요', '배부를 때 2차로 와서 고량주에 간단한 요리 시키기 좋은 곳.', 4.0),
(1, 2, '진짜 맛있는 중화요리', '웬만한 중국집보다 요리를 잘하는 곳. 술이 술술 들어갑니다.', 4.8),
(8, 2, '조금 아쉬웠어요', '가게가 협소하고 화장실이 불편해서 조금 아쉬웠습니다.', 3.0);
-- Tags for reviews 6-10
INSERT INTO review_tag (review_id, tag) VALUES
(6, '가성비'), (6, '안주맛집'),
(7, '단체회식'),
(8, '적합2차'),
(9, '안주맛집'),
(10, '그냥그래');

-- ===== Place: 열려라 맥주창고 (ID: 3) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(8, 3, '맥주 러버들의 성지', '다양한 세계맥주를 저렴하게 마실 수 있어서 너무 좋아요. 안주 반입도 가능!', 5.0),
(6, 3, '회식 2차 장소로 최고', '1차하고 와서 각자好きな맥주 골라 마시니 다들 좋아하더라고요.', 4.5),
(9, 3, '편하게 마시기 좋은 곳', '눈치 안 보고 편하게 마실 수 있는 분위기. 친구랑 수다떨기 좋아요.', 4.0),
(10, 3, '가성비 하나는 끝내줌', '정말 저렴하게 맥주를 즐길 수 있다는 것만으로도 올 가치가 충분합니다.', 4.8);
-- Tags for reviews 11-14
INSERT INTO review_tag (review_id, tag) VALUES
(11, '가성비'), (11, '적합2차'),
(12, '단체회식'), (12, '적합2차'),
(13, '대화하기좋음'),
(14, '가성비');

-- ===== Place: 서강포차 (ID: 4) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(2, 4, '학생 때 생각나는 곳', '정겨운 분위기의 포차입니다. 김치찌개가 정말 맛있어요.', 4.2),
(3, 4, '가성비가 내려옵니다', '주머니 가벼운 학생들에게는 최고의 술집이 아닐까 싶네요.', 4.5),
(4, 4, '이모님 손맛 최고!', '안주 하나하나에 정성이 느껴지는 곳. 이모님이 친절하셔서 더 좋아요.', 5.0),
(1, 4, '오래된 맛집', '오래된 곳이지만 관리가 잘 되어있고, 변하지 않는 맛이 있습니다.', 4.0);
-- Tags for reviews 15-18
INSERT INTO review_tag (review_id, tag) VALUES
(15, '분위기'), (15, '안주맛집'),
(16, '가성비'),
(17, '안주맛집'), (17, '직원친절'),
(18, '안주맛집');

-- ===== Place: 옹고집 (ID: 5) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(5, 5, '든든한 밥과 술', '보쌈에 막걸리 한잔 생각날 때 가는 곳. 식사 겸 반주하기 좋아요.', 4.3),
(7, 5, '집밥 같은 한식 안주', '자극적이지 않고 맛있는 한식 안주들이 많아서 속이 편안해요.', 4.5),
(1, 5, '언제나 만족스러운 곳', '꾸준히 방문하는 곳입니다. 제육볶음이 정말 맛있어요.', 4.8);
-- Tags for reviews 19-21
INSERT INTO review_tag (review_id, tag) VALUES
(19, '안주맛집'),
(20, '안주맛집'), (20, '대화하기좋음'),
(21, '안주맛집');

-- ===== Place: 낭만오지 (ID: 6) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(9, 6, '분위기 좋은 루프탑', '날씨 좋은 날 루프탑에서 맥주 한잔하기 최고! 분위기가 정말 좋아요.', 4.7),
(7, 6, '데이트하기 좋은 곳', '분위기가 좋아서 연인이랑 오기 좋은 곳이에요. 피맥하기 좋습니다.', 4.5),
(10, 6, '가격대는 좀 있지만..', '가격이 저렴하진 않지만 분위기가 좋아서 만족스러웠습니다.', 3.8),
(4, 6, '뷰가 다했다', '경의선 숲길이 보이는 뷰가 정말 멋져요. 자리 잘 잡아야 해요.', 4.0);
-- Tags for reviews 22-25
INSERT INTO review_tag (review_id, tag) VALUES
(22, '분위기'),
(23, '분위기'), (23, '대화하기좋음'),
(24, '가성비'), (24, '분위기'),
(25, '분위기');

-- ===== Place: 투다리 (ID: 7) =====
INSERT INTO review (user_id, place_id, title, comment, score) VALUES
(2, 7, '믿고 먹는 투다리', '어느 지점을 가도 맛있는 투다리. 김치우동은 진리입니다.', 4.5),
(5, 7, '혼술하기 좋아요', '다찌 자리가 있어서 혼자 가서 간단하게 한잔하기 좋습니다.', 4.2),
(8, 7, '가성비 꼬치구이', '저렴한 가격에 다양한 꼬치구이를 맛볼 수 있어서 좋아요.', 4.0);
-- Tags for reviews 26-28
INSERT INTO review_tag (review_id, tag) VALUES
(26, '안주맛집'),
(27, '대화하기좋음'),
(28, '가성비'), (28, '안주맛집');


-- =================================================================
-- 4. 음식점별 평점(score) 및 리뷰 수(total_reviews) 최종 UPDATE


UPDATE place SET score = 4.3, total_reviews = 5 WHERE place_id = 1; -- 고주파
UPDATE place SET score = 4.06, total_reviews = 5 WHERE place_id = 2; -- 홍등롱
UPDATE place SET score = 4.58, total_reviews = 4 WHERE place_id = 3; -- 열려라 맥주창고
UPDATE place SET score = 4.43, total_reviews = 4 WHERE place_id = 4; -- 서강포차
UPDATE place SET score = 4.53, total_reviews = 3 WHERE place_id = 5; -- 옹고집
UPDATE place SET score = 4.25, total_reviews = 4 WHERE place_id = 6; -- 낭만오지
UPDATE place SET score = 4.23, total_reviews = 3 WHERE place_id = 7; -- 투다리

UPDATE place SET total_reviews = 0, score = 0.0 WHERE place_id >= 8;