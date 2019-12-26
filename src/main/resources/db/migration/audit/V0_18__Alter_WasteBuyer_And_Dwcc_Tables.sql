
ALTER TABLE audit.dwccs_aud
  DROP COLUMN is_mou_moa_signed,
  ADD COLUMN is_mou_signed BOOLEAN,
  ADD COLUMN date_mou_signed DATE,
  ADD COLUMN date_mou_expires DATE,
  ADD COLUMN is_moa_signed BOOLEAN;


ALTER TABLE audit.waste_buyers_aud
  ADD COLUMN region_id INTEGER;
