DROP TABLE IF EXISTS TASK;
CREATE TABLE IF NOT EXISTS TASK (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    done BOOLEAN NOT NULL DEFAULT false
);