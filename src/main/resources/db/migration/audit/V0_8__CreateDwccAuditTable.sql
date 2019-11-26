
CREATE TABLE audit.dwccs_aud (
  id                    INTEGER NOT NULL,
  rev                   BIGINT  NOT NULL,
  revtype               SMALLINT,
  name                  citext,
  ward_id               INTEGER,
  owner_or_agency_name  CHARACTER VARYING(255),
  is_owned_and_operated BOOLEAN,
  date_operating_since  DATE,
  area_in_sqft          INTEGER,
  is_mou_moa_signed     BOOLEAN,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
