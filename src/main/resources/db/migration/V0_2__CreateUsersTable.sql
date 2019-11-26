CREATE TABLE authorized_users (
  id       SERIAL PRIMARY KEY,
  email    CHARACTER VARYING(255)    NOT NULL UNIQUE,
  name     CHARACTER VARYING(255)    NOT NULL,
  is_admin BOOLEAN DEFAULT FALSE     NOT NULL,
  uuid     UUID                      NOT NULL UNIQUE
);

INSERT INTO authorized_users
VALUES (
  default,
  'dwcc.admin@hasirudala.in',
  'Default Admin',
  true,
  uuid_generate_v4()
);
