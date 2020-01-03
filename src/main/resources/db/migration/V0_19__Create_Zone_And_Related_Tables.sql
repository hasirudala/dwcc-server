CREATE TABLE zones (
  id        SERIAL PRIMARY KEY,
  name      citext  NOT NULL,
  region_id INTEGER NOT NULL REFERENCES regions (id),
  uuid      UUID    NOT NULL UNIQUE,
  UNIQUE (name, region_id)
);

CREATE TABLE wards_zones (
  ward_id INTEGER REFERENCES wards (id) NOT NULL,
  zone_id INTEGER REFERENCES zones (id) NOT NULL
);
