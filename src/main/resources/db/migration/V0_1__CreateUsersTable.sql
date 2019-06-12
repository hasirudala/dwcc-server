CREATE TABLE users
(
    id                      SERIAL PRIMARY KEY,
    name                    CHARACTER VARYING(255) NOT NULL,
    uuid                    CHARACTER VARYING(255) NOT NULL,
    created_by_id           BIGINT                 NOT NULL,
    last_modified_by_id     BIGINT                 NOT NULL,
    created_date_time       TIMESTAMP              NOT NULL,
    last_modified_date_time TIMESTAMP              NOT NULL
);