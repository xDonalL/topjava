DELETE FROM meals;
DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-06-12 07:00', 'Завтрак', 500, 100000),
       ('2020-06-12 13:00', 'Обед', 1000, 100000),
       ('2020-06-12 18:00', 'Ужин', 600, 100000),
       ('2020-06-12 08:00', 'Админ завтрак', 500, 100001),
       ('2020-06-12 13:00', 'Админ обед', 700, 100001);
