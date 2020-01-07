ALTER TABLE waste_buyers
  ADD COLUMN zone_id INTEGER REFERENCES zones(id),
  ADD COLUMN address CHARACTER VARYING(255),
  ADD COLUMN is_kspcb_authorized BOOLEAN DEFAULT FALSE;

CREATE TABLE waste_buyers_items (
  buyer_id INTEGER REFERENCES waste_buyers(id),
  item_id INTEGER REFERENCES waste_items(id)
);
