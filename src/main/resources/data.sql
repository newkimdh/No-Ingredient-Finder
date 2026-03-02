-- 1. 식당 등록
INSERT INTO restaurant (name, address, category) VALUES ('건대 파스타', '광진구 화양동', '양식');

-- 2. 메뉴 등록 (식당 ID 1번에 연결)
INSERT INTO menu (name, restaurant_id) VALUES ('토마토 파스타', 1);
INSERT INTO menu (name, restaurant_id) VALUES ('알리오 올리오', 1);

-- 3. 재료 등록
INSERT INTO ingredient (name) VALUES ('토마토');
INSERT INTO ingredient (name) VALUES ('마늘');

-- 4. 메뉴-재료 연결 (N:M 매핑 테이블)
INSERT INTO menu_ingredient (menu_id, ingredient_id) VALUES (1, 1); -- 토마토 파스타에 토마토
INSERT INTO menu_ingredient (menu_id, ingredient_id) VALUES (2, 2); -- 알리오 올리오에 마늘