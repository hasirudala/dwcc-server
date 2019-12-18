CREATE TABLE audit.vehicle_types_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CHARACTER VARYING(255),
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
