CREATE TABLE audit.expense_types_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CITEXT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.expense_tags_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CITEXT,
  type_id INTEGER,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.expense_items_aud (
  id            INTEGER NOT NULL,
  rev           BIGINT  NOT NULL,
  revtype       SMALLINT,
  name          CITEXT,
  type_id       INTEGER,
  ask_num_units BOOLEAN,
  unit_label    CHARACTER VARYING(31),
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.expense_items_tags_aud (
  rev     BIGINT NOT NULL,
  revtype SMALLINT,
  item_id INTEGER,
  tag_id  INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
