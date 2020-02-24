ALTER TABLE audit.outgoing_waste_entries_aud
  DROP COLUMN reject_quantity;

ALTER TABLE audit.outgoing_waste_records_aud
  ADD COLUMN reject_quantity NUMERIC;

ALTER TABLE audit.outgoing_waste_records_aud
  RENAME COLUMN sanitary_waste_quantity to sanitary_quantity;
