ALTER TABLE dwccs
  DROP COLUMN is_mou_moa_signed,
  ADD COLUMN is_mou_signed BOOLEAN,
  ADD COLUMN date_mou_signed DATE,
  ADD COLUMN date_mou_expires DATE,
  ADD COLUMN is_moa_signed BOOLEAN;

UPDATE outgoing_waste_items
SET buyer_id = NULL;

DELETE FROM waste_buyers;

ALTER TABLE waste_buyers
  ADD COLUMN region_id INTEGER REFERENCES regions (id) NOT NULL;
