CREATE TABLE audit.authorized_users_aud (
  id                      INTEGER NOT NULL,
  rev                     BIGINT NOT NULL,
  revtype                 SMALLINT,
  email                   CHARACTER VARYING(255),
  name                    CHARACTER VARYING(255),
  is_admin                BOOLEAN,
  is_disabled             BOOLEAN,
  is_faux_deleted         BOOLEAN,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
