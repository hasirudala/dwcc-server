CREATE TABLE waste_sources (
  id   SERIAL PRIMARY KEY,
  name CITEXT NOT NULL UNIQUE,
  uuid UUID   NOT NULL UNIQUE
);

ALTER TABLE incoming_waste_entries_mixed
  ADD COLUMN source_id INTEGER REFERENCES waste_sources (id),
  ADD COLUMN bill_number CITEXT UNIQUE;
