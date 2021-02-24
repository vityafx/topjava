DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_meals_seq;


CREATE SEQUENCE global_meals_seq START WITH 100000;

CREATE TABLE meals
(
    id          INTEGER primary key DEFAULT nextval('global_meals_seq'),
    user_id INTEGER NOT NULL ,
    dateTime    TIMESTAMP    NOT NULL,
    description VARCHAR NOT NULL,
    calories    INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT meal_idx UNIQUE (user_id, dateTime)
);