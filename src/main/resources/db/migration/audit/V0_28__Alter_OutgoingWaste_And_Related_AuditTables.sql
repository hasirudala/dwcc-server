DELETE FROM audit.outgoing_waste_items_aud;
DELETE FROM audit.outgoing_waste_records_aud;

ALTER TABLE audit.outgoing_waste_records_aud
  DROP COLUMN date,
  ADD COLUMN from_date DATE,
  ADD COLUMN to_date DATE,
  ADD COLUMN total_quantity NUMERIC,
  ADD COLUMN sanitary_waste_quantity NUMERIC;

ALTER TABLE audit.outgoing_waste_items_aud
  RENAME TO outgoing_waste_entries_aud;

ALTER TABLE audit.outgoing_waste_entries_aud
  DROP COLUMN item_id,
  ADD COLUMN reject_quantity NUMERIC,
  ADD COLUMN stock_in_hand NUMERIC;

CREATE TABLE audit.outgoing_waste_entry_items_aud (
  rev      BIGINT NOT NULL,
  revtype  SMALLINT,
  entry_id BIGINT,
  item_id  INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
