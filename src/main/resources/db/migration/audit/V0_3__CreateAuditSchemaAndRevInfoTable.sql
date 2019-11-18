CREATE SCHEMA audit;

CREATE TABLE audit.revinfo (
  rev      BIGINT PRIMARY KEY,
  revtstmp BIGINT,
  user_id  VARCHAR(255)
);
