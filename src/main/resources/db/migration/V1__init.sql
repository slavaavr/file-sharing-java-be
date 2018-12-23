CREATE TABLE IF NOT EXISTS userr
(
  id       serial PRIMARY KEY,
  email    VARCHAR(50) NOT NULL UNIQUE,
  nickname VARCHAR(50) NOT NULL,
  password CHAR(60)    NOT NULL
  );

CREATE TABLE IF NOT EXISTS file
(
  id                 serial PRIMARY KEY,
  user_id            INTEGER     NULL REFERENCES userr (id) ON DELETE CASCADE,
  title              VARCHAR(60) NOT NULL,
  body               BYTEA       NOT NULL,
  file_type          VARCHAR(50) NOT NULL,
  file_size          VARCHAR(10) NOT NULL,
  file_storage_time  INTERVAL    NOT NULL,
  file_creation_date TIMESTAMP            DEFAULT NOW(),
  count_download     SMALLINT             DEFAULT 0
  );

CREATE TABLE IF NOT EXISTS tag
(
  id          serial PRIMARY KEY,
  name        VARCHAR(30) NOT NULL,
  use_counter INTEGER DEFAULT 0
  );

CREATE TABLE IF NOT EXISTS file_tag
(
  file_id INTEGER REFERENCES file (id) ON DELETE CASCADE,
  tag_id  INTEGER REFERENCES tag (id),
  CONSTRAINT file_tag_unique UNIQUE (file_id, tag_id)
  );

CREATE PROCEDURE erase_old_files()
LANGUAGE plpgsql
AS
$$
BEGIN
  DELETE
  FROM file
  WHERE file_date < NOW() - file_lifetime;
END;
$$;
