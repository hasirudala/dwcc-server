ALTER TABLE audit.expense_purchase_entries_aud
  DROP COLUMN waste_item_id;

CREATE TABLE audit.expense_purchase_entry_items_aud (
  rev               BIGINT NOT NULL,
  revtype           SMALLINT,
  purchase_entry_id BIGINT,
  waste_item_id     INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
