DELETE FROM expense_purchase_entries;

ALTER TABLE expense_purchase_entries
  DROP COLUMN waste_item_id;

CREATE TABLE expense_purchase_entry_items (
  purchase_entry_id BIGINT REFERENCES expense_purchase_entries (id)  NOT NULL,
  waste_item_id     INTEGER REFERENCES waste_items (id)              NOT NULL
);
