CREATE USER IF NOT EXISTS 'prospring5' IDENTIFIED BY 'prospring5';

DROP DATABASE IF EXISTS publishing_mvc;

CREATE SCHEMA publishing_mvc;

GRANT ALL PRIVILEGES ON publishing_mvc.* TO 'prospring5';
FLUSH PRIVILEGES;

/* Clarify timezone in case of java.sql.SQLException */
SET GLOBAL time_zone = '+5:00';

