CREATE TABLE IF NOT EXISTS userr
(
  id       serial PRIMARY KEY,
  email    VARCHAR(50) NOT NULL UNIQUE,
  nickname VARCHAR(50) NOT NULL,
  password CHAR(60)    NOT NULL
);

CREATE TABLE IF NOT EXISTS file
(
  id             serial PRIMARY KEY,
  user_id        INTEGER     NULL REFERENCES userr (id) ON DELETE CASCADE,
  uri            VARCHAR(60) NOT NULL UNIQUE,
  title          VARCHAR(60) NOT NULL,
  body           BYTEA       NOT NULL,
  type           VARCHAR(50) NOT NULL,
  size           BIGINT      NOT NULL,
  storage_time   VARCHAR(50) NOT NULL,
  creation_date  TIMESTAMP DEFAULT NOW(),
  count_download SMALLINT  DEFAULT 0
);

CREATE TABLE IF NOT EXISTS tag
(
  id          serial PRIMARY KEY,
  title       VARCHAR(30) UNIQUE NOT NULL,
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
  WHERE creation_date > NOW() - storage_time;
END;
$$;
