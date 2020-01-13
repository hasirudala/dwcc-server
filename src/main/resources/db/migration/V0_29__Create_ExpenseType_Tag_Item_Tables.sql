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
  unit_label    CHARACTER VARYING(31),
  uuid          UUID   NOT NULL UNIQUE
);

CREATE TABLE expense_items_tags (
  item_id INTEGER REFERENCES expense_items (id),
  tag_id  INTEGER REFERENCES expense_tags (id)
);

INSERT INTO expense_types VALUES (1, 'Manpower', uuid_generate_v4());
INSERT INTO expense_types VALUES (2, 'Operations & Maintenance', uuid_generate_v4());

INSERT INTO expense_tags VALUES (1, 'Utility', 2, uuid_generate_v4());
INSERT INTO expense_tags VALUES (2, 'Consumable', 2, uuid_generate_v4());

INSERT INTO expense_items VALUES (1, 'Sorters (Male)', 1, true, null, uuid_generate_v4());
INSERT INTO expense_items VALUES (2, 'Sorters (Female)', 1, true, null, uuid_generate_v4());
INSERT INTO expense_items VALUES (3, 'Drivers', 1, true, null, uuid_generate_v4());
INSERT INTO expense_items VALUES (4, 'Helpers', 1, true, null, uuid_generate_v4());
INSERT INTO expense_items VALUES (5, 'Water', 2, false, null, uuid_generate_v4());
INSERT INTO expense_items VALUES (6, 'Electricity', 2, false, null, uuid_generate_v4());

INSERT INTO expense_items_tags VALUES (5, 1);
INSERT INTO expense_items_tags VALUES (6, 1);

