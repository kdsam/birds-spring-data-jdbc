DROP TABLE bird IF EXISTS;

CREATE TABLE bird (
  id         INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  breed  VARCHAR(30)
);