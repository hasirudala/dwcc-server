CREATE TABLE regions (
  id   SERIAL PRIMARY KEY,
  name CHARACTER VARYING(255) NOT NULL UNIQUE,
  uuid UUID                   NOT NULL UNIQUE
);

CREATE TABLE wards (
  id        SERIAL PRIMARY KEY,
  name      citext  NOT NULL,
  region_id INTEGER NOT NULL,
  uuid      UUID    NOT NULL UNIQUE,
  UNIQUE (name, region_id),
  FOREIGN KEY (region_id) REFERENCES regions (id)
);
