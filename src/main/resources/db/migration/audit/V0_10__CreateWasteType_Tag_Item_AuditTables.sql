CREATE TABLE audit.waste_types_aud (
  id      INTEGER NOT NULL,
  rev     BIGINT  NOT NULL,
  revtype SMALLINT,
  name    CITEXT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.waste_tags_aud (
  id            INTEGER NOT NULL,
  rev           BIGINT  NOT NULL,
  revtype       SMALLINT,
  name          CITEXT,
  waste_type_id INTEGER,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.waste_items_aud (
  id            INTEGER NOT NULL,
  rev           BIGINT  NOT NULL,
  revtype       SMALLINT,
  name          CITEXT,
  waste_type_id INTEGER,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);

CREATE TABLE audit.waste_items_waste_tags_aud (
  rev           BIGINT  NOT NULL,
  revtype       SMALLINT,
  waste_item_id INTEGER,
  waste_tag_id  INTEGER,
  FOREIGN KEY (rev) REFERENCES audit.revinfo (rev)
);
