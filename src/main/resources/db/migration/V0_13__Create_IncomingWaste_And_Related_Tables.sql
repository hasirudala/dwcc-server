CREATE TABLE incoming_waste_records (
  id                SERIAL PRIMARY KEY,
  date              DATE    NOT NULL,
  dwcc_id           INTEGER NOT NULL REFERENCES dwccs (id),
  errors_ignored    BOOLEAN DEFAULT FALSE,
  approved_by_admin BOOLEAN DEFAULT FALSE,
  notes             TEXT,
  uuid              UUID    NOT NULL UNIQUE
);

CREATE TABLE incoming_waste_dtd (
  id              SERIAL PRIMARY KEY,
  vehicle_number  VARCHAR(32)                                       NOT NULL,
  vehicle_type_id INTEGER REFERENCES vehicle_types (id)             NOT NULL,
  quantity        NUMERIC                                           NOT NULL,
  record_id       BIGINT REFERENCES incoming_waste_records (id)     NOT NULL,
  uuid            UUID                                              NOT NULL UNIQUE
);

CREATE TABLE incoming_waste_items (
  id              SERIAL PRIMARY KEY,
  item_id         INTEGER REFERENCES waste_items (id)              NOT NULL,
  quantity        NUMERIC                                          NOT NULL,
  reject_quantity NUMERIC,
  record_id       BIGINT REFERENCES incoming_waste_records (id)    NOT NULL,
  uuid            UUID                                             NOT NULL UNIQUE
);

CREATE TABLE incoming_waste_mixed (
  id        SERIAL PRIMARY KEY,
  quantity  NUMERIC                                            NOT NULL,
  rate      NUMERIC,
  record_id BIGINT REFERENCES incoming_waste_records (id)      NOT NULL,
  uuid      UUID                                               NOT NULL UNIQUE
);

CREATE TABLE incoming_waste_mixed_items (
  mixed_waste_id BIGINT REFERENCES incoming_waste_mixed (id) NOT NULL,
  item_id        INTEGER REFERENCES waste_items (id)         NOT NULL
);
