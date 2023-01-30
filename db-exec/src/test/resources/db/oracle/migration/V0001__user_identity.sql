
-- Convention: GQ Id 519

--
-- !!!
-- TODO: Rewrite SEQUENCE to GENERATED ALWAYS AS IDENTITY (NOCACHE)
-- !!!
--
--

ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN';

CREATE USER "${schemaNameUI}"
  NO AUTHENTICATION
  CONTAINER = CURRENT
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;

--
-- Why do I use ROLEs to manage user privileges?
-- Using ROLEs like this seems to be well established "design pattern" among Oracle DBAs.
-- One important benefit is that it makes it easier to work around Oracle limitation that you can't
-- grant SELECT (UPDATE, INSERT, ...) on all tables in particular schema with one statement.
-- It could be done in a loop (possibly run after all migration that creates any new database object), but that's
-- something I would rather avoid. For reference see:
--   * https://stackoverflow.com/questions/187886/grant-select-on-all-tables-owned-by-specific-user/189496
--   * https://www.oracletutorial.com/oracle-administration/oracle-grant-select/
--

-- Roles for accessing User Identity schema
CREATE ROLE "${schemaNameUI}_READONLY_ROLE";
CREATE ROLE "${schemaNameUI}_UPDATE_ROLE";

-- User Identity application user
CREATE USER "${appUserNameUI}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${appUserPasswordUI}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameUI}_READONLY_ROLE" TO "${appUserNameUI}";
GRANT "${schemaNameUI}_UPDATE_ROLE" TO "${appUserNameUI}";
ALTER USER "${appUserNameUI}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${appUserNameUI}";

-- User Identity read-only (reporting) user
CREATE USER "${roUserNameUI}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${roUserPasswordUI}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameUI}_READONLY_ROLE" TO "${roUserNameUI}";
ALTER USER "${roUserNameUI}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${roUserNameUI}";


-- UserIdentity
CREATE TABLE "${schemaNameUI}"."UserIdentity" (
  "id"           NUMBER(19, 0),
  "username"     VARCHAR2(255 BYTE),
  "password"     VARCHAR2(255 BYTE),
  CONSTRAINT PK_UserIdentity PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";


CREATE SEQUENCE "${schemaNameUI}"."SEQ_UserIdentity"
  MINVALUE 1 MAXVALUE 999999999999999999999999999
  START WITH 1 INCREMENT BY 1
  CACHE 20
  NOORDER
  NOCYCLE;
GRANT SELECT ON "${schemaNameUI}"."SEQ_UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";

-- todo: CREATE SYNONYM
