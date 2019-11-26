CREATE TABLE dwccs (
  id                    SERIAL PRIMARY KEY,
  name                  citext  NOT NULL,
  ward_id               INTEGER NOT NULL,
  owner_or_agency_name  CHARACTER VARYING(255),
  is_owned_and_operated BOOLEAN,
  date_operating_since  DATE,
  area_in_sqft          INTEGER,
  is_mou_moa_signed     BOOLEAN,
  uuid                  UUID    NOT NULL UNIQUE,
  UNIQUE (name, ward_id),
  FOREIGN KEY (ward_id) REFERENCES wards (id)
);
