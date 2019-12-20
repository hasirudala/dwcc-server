CREATE TABLE waste_buyers (
  id   SERIAL PRIMARY KEY,
  name CITEXT NOT NULL UNIQUE,
  uuid UUID   NOT NULL UNIQUE
);

CREATE TABLE outgoing_waste_records (
  id      SERIAL PRIMARY KEY,
  date    DATE    NOT NULL,
  dwcc_id INTEGER NOT NULL REFERENCES dwccs (id),
  notes   TEXT,
  uuid    UUID    NOT NULL UNIQUE
);

CREATE TABLE outgoing_waste_items (
  id        SERIAL PRIMARY KEY,
  item_id   INTEGER REFERENCES waste_items (id)              NOT NULL,
  quantity  NUMERIC                                          NOT NULL,
  rate      NUMERIC,
  buyer_id  BIGINT REFERENCES waste_buyers (id),
  record_id BIGINT REFERENCES outgoing_waste_records (id)    NOT NULL,
  uuid      UUID                                             NOT NULL UNIQUE
);
