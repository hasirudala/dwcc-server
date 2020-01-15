CREATE TABLE expense_types (
  id   SERIAL PRIMARY KEY,
  name CITEXT NOT NULL UNIQUE,
  uuid UUID   NOT NULL UNIQUE
);

CREATE TABLE expense_tags (
  id      SERIAL PRIMARY KEY,
  name    CITEXT NOT NULL UNIQUE,
  type_id INTEGER REFERENCES expense_types (id),
  uuid    UUID   NOT NULL UNIQUE
);

CREATE TABLE expense_items (
  id            SERIAL PRIMARY KEY,
  name          CITEXT NOT NULL UNIQUE,
  type_id       INTEGER REFERENCES expense_types (id),
  ask_num_units BOOLEAN DEFAULT FALSE,
  uuid          UUID   NOT NULL UNIQUE
);

CREATE TABLE expense_items_tags (
  item_id INTEGER REFERENCES expense_items (id),
  tag_id  INTEGER REFERENCES expense_tags (id)
);
