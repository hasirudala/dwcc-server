CREATE TABLE audit.zones_aud (
  id        INTEGER NOT NULL,
  rev       BIGINT  NOT NULL,
  revtype   SMALLINT,
  name      citext,
  region_id INTEGER,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.wards_zones_aud (
  rev     BIGINT NOT NULL,
  revtype SMALLINT,
  ward_id INTEGER,
  zone_id INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
