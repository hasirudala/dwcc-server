CREATE TABLE audit.waste_sources_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CITEXT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

ALTER TABLE audit.incoming_waste_entries_mixed_aud
  ADD COLUMN source_id INTEGER,
  ADD COLUMN bill_number TEXT;
