CREATE TABLE audit.expense_records_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  date    DATE,
  dwcc_id INTEGER,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.expense_entries_aud (
  id              INTEGER NOT NULL,
  rev             BIGINT  NOT NULL,
  revtype         SMALLINT,
  expense_item_id INTEGER,
  num_items       NUMERIC,
  amount          NUMERIC,
  record_id       BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.expense_purchase_entries_aud (
  id            INTEGER NOT NULL,
  rev           BIGINT  NOT NULL,
  revtype       SMALLINT,
  waste_item_id INTEGER,
  amount        NUMERIC,
  record_id     BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
