CREATE TABLE audit.waste_buyers_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CITEXT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.outgoing_waste_records_aud (
  id                INTEGER NOT NULL,
  rev               BIGINT  NOT NULL,
  revtype           SMALLINT,
  date              DATE,
  dwcc_id           INTEGER,
  notes             TEXT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.outgoing_waste_items_aud (
  id        INTEGER NOT NULL,
  rev       BIGINT  NOT NULL,
  revtype   SMALLINT,
  item_id   INTEGER,
  quantity  NUMERIC,
  rate      NUMERIC,
  buyer_id  BIGINT,
  record_id BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
