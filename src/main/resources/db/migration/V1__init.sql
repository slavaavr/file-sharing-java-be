CREATE TABLE IF NOT EXISTS _user
(
  id       serial PRIMARY KEY,
  email    VARCHAR(50) NOT NULL UNIQUE,
  nickname VARCHAR(50) NOT NULL,
  password CHAR(60)    NOT NULL
  );

CREATE TYPE FILE_TYPE AS ENUM ('file', 'video', 'audio', 'image');

CREATE TABLE IF NOT EXISTS file
(
  id             serial PRIMARY KEY,
  user_id        INTEGER     NULL REFERENCES _user (id) ON DELETE CASCADE,
  title          VARCHAR(60) NOT NULL DEFAULT '',
  body           bytea       NOT NULL,
  file_type      FILE_TYPE   NOT NULL DEFAULT 'file',
  file_size      VARCHAR(10) NOT NULL,
  file_lifetime  INTERVAL    NOT NULL,
  file_date      TIMESTAMP            DEFAULT NOW(),
  count_download SMALLINT             DEFAULT 0
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
