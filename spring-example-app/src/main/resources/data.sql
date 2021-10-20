INSERT INTO people (id, version, name, birth_date, gender_code, memo, created_at, created_by, updated_at, updated_by)
VALUES (1, 0, '鈴木 一郎', '20000112', '1', 'テスト1', '2021-01-12 09:12:23', 'TEST', '2021-01-12 09:12:23', 'TEST');
INSERT INTO people (id, version, name, birth_date, gender_code, memo, created_at, created_by, updated_at, updated_by)
VALUES (2, 0, '山田 花子', '20010223', '2', 'テスト2', '2021-01-14 12:23:34', 'TEST', '2021-01-14 12:23:34', 'TEST');

INSERT INTO genders (gender_code, gender_name) VALUES ('1', '男');
INSERT INTO genders (gender_code, gender_name) VALUES ('2', '女');
INSERT INTO genders (gender_code, gender_name) VALUES ('3', 'その他');
