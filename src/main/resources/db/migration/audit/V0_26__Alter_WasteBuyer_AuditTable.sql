ALTER TABLE audit.waste_buyers_aud
  ADD COLUMN zone_id INTEGER,
  ADD COLUMN address CHARACTER VARYING(255),
  ADD COLUMN is_kspcb_authorized BOOLEAN;

CREATE TABLE audit.waste_buyers_items_aud (
  rev      BIGINT NOT NULL,
  revtype  SMALLINT,
  buyer_id INTEGER,
  item_id  INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
