CREATE TABLE Movie (
  id            UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  "name"        TEXT NOT NULL,
  "year"        NUMERIC NOT NULL,
  "director"    TEXT
);
