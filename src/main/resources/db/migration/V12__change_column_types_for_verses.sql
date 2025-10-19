ALTER TABLE verses
ALTER COLUMN chapter TYPE integer USING chapter::integer,
  ALTER COLUMN verse   TYPE integer USING verse::integer;
