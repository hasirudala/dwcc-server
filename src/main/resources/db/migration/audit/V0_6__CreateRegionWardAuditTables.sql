CREATE TABLE audit.regions_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CHARACTER VARYING(255),
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.wards_aud (
  id        INTEGER NOT NULL,
  rev       BIGINT  NOT NULL,
  revtype   SMALLINT,
  name      citext,
  region_id INTEGER,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
