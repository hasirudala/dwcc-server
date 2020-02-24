ALTER TABLE outgoing_waste_entries
  DROP COLUMN reject_quantity;

ALTER TABLE outgoing_waste_records
  ADD COLUMN reject_quantity NUMERIC;

ALTER TABLE outgoing_waste_records
  RENAME COLUMN sanitary_waste_quantity to sanitary_quantity;
