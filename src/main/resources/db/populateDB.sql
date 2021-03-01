DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(user_id, datetime, description, calories)
VALUES (100000, '2021-02-24 10:00', 'breakfast', 700),
       (100000, '2021-02-24 11:00', 'snack', 500),
       (100000, '2021-02-24 12:00', 'lunch', 1200),
       (100000, '2021-02-24 14:00', 'dinner', 500),
       (100000, '2021-02-24 16:00', 'snack', 300),
       (100001, '2021-02-24 09:00', 'breakfast', 500),
       (100001, '2021-02-24 11:00', 'snack', 300);
