CREATE TABLE waste_types (
  id   SERIAL PRIMARY KEY,
  name CITEXT NOT NULL UNIQUE,
  uuid UUID   NOT NULL UNIQUE
);

CREATE TABLE waste_tags (
  id            SERIAL PRIMARY KEY,
  name          CITEXT NOT NULL UNIQUE,
  waste_type_id INTEGER REFERENCES waste_types (id),
  uuid          UUID   NOT NULL UNIQUE
);

CREATE TABLE waste_items (
  id            SERIAL PRIMARY KEY,
  name          CITEXT NOT NULL,
  waste_type_id INTEGER REFERENCES waste_types (id),
  uuid          UUID   NOT NULL UNIQUE,
  UNIQUE (name, waste_type_id)
);

CREATE TABLE waste_items_waste_tags (
  waste_item_id INTEGER REFERENCES waste_items (id) NOT NULL,
  waste_tag_id  INTEGER REFERENCES waste_tags (id)  NOT NULL
);
