CREATE TABLE expense_records (
  id      SERIAL PRIMARY KEY,
  date    DATE                          NOT NULL,
  dwcc_id INTEGER REFERENCES dwccs (id) NOT NULL,
  uuid    UUID                          NOT NULL UNIQUE
);

CREATE TABLE expense_entries (
  id              SERIAL PRIMARY KEY,
  expense_item_id INTEGER REFERENCES expense_items (id)  NOT NULL,
  num_items       NUMERIC,
  amount          NUMERIC                                NOT NULL,
  record_id       BIGINT REFERENCES expense_records (id) NOT NULL,
  uuid            UUID                                   NOT NULL UNIQUE
);

CREATE TABLE expense_purchase_entries (
  id            SERIAL PRIMARY KEY,
  waste_item_id INTEGER REFERENCES waste_items (id)    NOT NULL,
  amount        NUMERIC                                NOT NULL,
  record_id     BIGINT REFERENCES expense_records (id) NOT NULL,
  uuid          UUID                                   NOT NULL UNIQUE
);
