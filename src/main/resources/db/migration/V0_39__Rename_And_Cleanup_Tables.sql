ALTER TABLE waste_items_waste_tags
  RENAME TO waste_items_tags;

ALTER TABLE incoming_waste_dtd
  RENAME TO incoming_waste_entries_dtd;

ALTER TABLE incoming_waste_mixed
  RENAME TO incoming_waste_entries_mixed;

ALTER TABLE incoming_waste_mixed_items
  RENAME TO incoming_waste_entries_mixed_items;

DROP TABLE incoming_waste_items;

ALTER TABLE incoming_waste_records
  DROP COLUMN errors_ignored,
  DROP COLUMN approved_by_admin;

ALTER TABLE incoming_waste_entries_dtd
  ALTER COLUMN reject_qty DROP NOT NULL,
  ALTER COLUMN reject_qty DROP DEFAULT,
  ALTER COLUMN sanitary_qty DROP NOT NULL,
  ALTER COLUMN sanitary_qty DROP DEFAULT;

ALTER TABLE incoming_waste_entries_dtd
  RENAME COLUMN quantity to total_kgs;

ALTER TABLE incoming_waste_entries_dtd
  RENAME COLUMN reject_qty to reject_kgs;

ALTER TABLE incoming_waste_entries_dtd
  RENAME COLUMN sanitary_qty TO sanitary_kgs;

ALTER TABLE incoming_waste_entries_mixed
  ALTER COLUMN reject_qty DROP NOT NULL,
  ALTER COLUMN reject_qty DROP DEFAULT;

ALTER TABLE incoming_waste_entries_mixed
  RENAME COLUMN quantity to total_kgs;

ALTER TABLE incoming_waste_entries_mixed
  RENAME COLUMN rate to rate_per_kg;

ALTER TABLE incoming_waste_entries_mixed
  RENAME COLUMN reject_qty to reject_kgs;

ALTER TABLE incoming_waste_entries_mixed_items
  RENAME COLUMN mixed_waste_id to entry_id;

ALTER TABLE outgoing_waste_records
  RENAME COLUMN total_quantity to total_kgs;

ALTER TABLE outgoing_waste_records
  RENAME COLUMN reject_quantity to reject_kgs;

ALTER TABLE outgoing_waste_records
  RENAME COLUMN sanitary_quantity to sanitary_kgs;

ALTER TABLE outgoing_waste_entries
  RENAME COLUMN quantity to quantity_kgs;

ALTER TABLE outgoing_waste_entries
  RENAME COLUMN stock_in_hand to stock_in_hand_kgs;

ALTER TABLE outgoing_waste_entries
  RENAME COLUMN rate to rate_per_kg;
