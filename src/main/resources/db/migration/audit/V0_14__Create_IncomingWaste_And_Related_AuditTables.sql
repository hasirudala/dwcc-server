CREATE TABLE audit.incoming_waste_records_aud (
  id                INTEGER NOT NULL,
  rev               BIGINT  NOT NULL,
  revtype           SMALLINT,
  date              DATE,
  dwcc_id           INTEGER,
  errors_ignored    BOOLEAN,
  approved_by_admin BOOLEAN,
  notes             TEXT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.incoming_waste_dtd_aud (
  id              INTEGER NOT NULL,
  rev             BIGINT  NOT NULL,
  revtype         SMALLINT,
  vehicle_number  VARCHAR(32),
  vehicle_type_id INTEGER,
  quantity        NUMERIC,
  record_id       BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.incoming_waste_items_aud (
  id              INTEGER NOT NULL,
  rev             BIGINT  NOT NULL,
  revtype         SMALLINT,
  item_id         INTEGER,
  quantity        NUMERIC,
  reject_quantity NUMERIC,
  record_id       BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.incoming_waste_mixed_aud (
  id        INTEGER NOT NULL,
  rev       BIGINT  NOT NULL,
  revtype   SMALLINT,
  quantity  NUMERIC,
  rate      NUMERIC,
  record_id BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.incoming_waste_mixed_items_aud (
  rev            BIGINT  NOT NULL,
  revtype        SMALLINT,
  mixed_waste_id BIGINT,
  item_id        INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
