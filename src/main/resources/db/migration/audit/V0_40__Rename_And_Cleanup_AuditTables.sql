ALTER TABLE audit.waste_items_waste_tags_aud
  RENAME TO waste_items_tags_aud;

ALTER TABLE audit.incoming_waste_dtd_aud
  RENAME TO incoming_waste_entries_dtd_aud;

ALTER TABLE audit.incoming_waste_mixed_aud
  RENAME TO incoming_waste_entries_mixed_aud;

ALTER TABLE audit.incoming_waste_mixed_items_aud
  RENAME TO incoming_waste_entries_mixed_items_aud;

ALTER TABLE audit.incoming_waste_records_aud
  DROP COLUMN errors_ignored,
  DROP COLUMN approved_by_admin;

ALTER TABLE audit.incoming_waste_entries_dtd_aud
  RENAME COLUMN quantity to total_kgs;

ALTER TABLE audit.incoming_waste_entries_dtd_aud
  RENAME COLUMN reject_qty to reject_kgs;

ALTER TABLE audit.incoming_waste_entries_dtd_aud
  RENAME COLUMN sanitary_qty TO sanitary_kgs;

ALTER TABLE audit.incoming_waste_entries_mixed_aud
  RENAME COLUMN quantity to total_kgs;

ALTER TABLE audit.incoming_waste_entries_mixed_aud
  RENAME COLUMN rate to rate_per_kg;

ALTER TABLE audit.incoming_waste_entries_mixed_aud
  RENAME COLUMN reject_qty to reject_kgs;

ALTER TABLE audit.incoming_waste_entries_mixed_items_aud
  RENAME COLUMN mixed_waste_id to entry_id;

ALTER TABLE audit.outgoing_waste_records_aud
  RENAME COLUMN total_quantity to total_kgs;

ALTER TABLE audit.outgoing_waste_records_aud
  RENAME COLUMN reject_quantity to reject_kgs;

ALTER TABLE audit.outgoing_waste_records_aud
  RENAME COLUMN sanitary_quantity to sanitary_kgs;

ALTER TABLE audit.outgoing_waste_entries_aud
  RENAME COLUMN quantity to quantity_kgs;

ALTER TABLE audit.outgoing_waste_entries_aud
  RENAME COLUMN stock_in_hand to stock_in_hand_kgs;

ALTER TABLE audit.outgoing_waste_entries_aud
  RENAME COLUMN rate to rate_per_kg;
