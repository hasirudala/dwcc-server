DELETE FROM outgoing_waste_items;
DELETE FROM outgoing_waste_records;

ALTER TABLE outgoing_waste_records
  DROP COLUMN date,
  ADD COLUMN from_date DATE NOT NULL,
  ADD COLUMN to_date DATE NOT NULL,
  ADD COLUMN total_quantity NUMERIC,
  ADD COLUMN sanitary_waste_quantity NUMERIC;

ALTER TABLE outgoing_waste_items
  RENAME TO outgoing_waste_entries;

ALTER TABLE outgoing_waste_entries
  DROP COLUMN item_id,
  ADD COLUMN reject_quantity NUMERIC,
  ADD COLUMN stock_in_hand NUMERIC;

CREATE TABLE outgoing_waste_entry_items (
  entry_id BIGINT REFERENCES outgoing_waste_entries (id),
  item_id  INTEGER REFERENCES waste_items (id)
);
