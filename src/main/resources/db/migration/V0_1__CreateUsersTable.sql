CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

SET TIMEZONE='Asia/Kolkata';

CREATE TABLE authorized_users
(
    id                      SERIAL PRIMARY KEY,
    email                   CHARACTER VARYING(255)    NOT NULL UNIQUE,
    name                    CHARACTER VARYING(255)    NOT NULL,
    is_admin                BOOLEAN DEFAULT FALSE     NOT NULL,
    is_disabled             BOOLEAN DEFAULT FALSE     NOT NULL,
    is_faux_deleted         BOOLEAN DEFAULT FALSE     NOT NULL,
    uuid                    CHARACTER VARYING(255)    NOT NULL UNIQUE
);

INSERT INTO authorized_users
  VALUES (
    default,
    'dwcc.admin@hasirudala.in',
    'Default Admin',
    true,
    false,
    false,
    uuid_generate_v4()
);

CREATE OR REPLACE FUNCTION trigger_uniqify_email_id_on_faux_delete() RETURNS trigger AS $$
BEGIN
  IF old.is_faux_deleted = false AND new.is_faux_deleted = true THEN
    new.email = old.email ||' [deleted on '|| to_char(CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS') ||']';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';


CREATE TRIGGER uniqify_email_id_on_faux_delete BEFORE UPDATE ON authorized_users
FOR EACH ROW
EXECUTE PROCEDURE trigger_uniqify_email_id_on_faux_delete();
