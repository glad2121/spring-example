CREATE TABLE people
(
  id          BIGINT        NOT NULL,
  version     BIGINT        NOT NULL,
  name        VARCHAR(40)   NOT NULL,
  birth_date  CHAR(8)       NOT NULL,
  gender_code CHAR(1)       NOT NULL,
  memo        VARCHAR(1024),
  created_at  TIMESTAMP     NOT NULL,
  created_by  VARCHAR(40)   NOT NULL,
  updated_at  TIMESTAMP     NOT NULL,
  updated_by  VARCHAR(40)   NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE genders
(
  gender_code CHAR(1)       NOT NULL,
  gender_name VARCHAR(4)    NOT NULL,
  PRIMARY KEY (gender_code)
);
