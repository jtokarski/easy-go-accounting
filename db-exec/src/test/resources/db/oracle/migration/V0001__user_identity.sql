
-- Convention: GQ Id 519

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
  "id"           NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "username"     VARCHAR2(255 BYTE),
  "password"     VARCHAR2(255 BYTE),
  CONSTRAINT PK_UserIdentity PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameUI}"."UserIdentity" TO "${schemaNameUI}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameUI}"."UserIdentity" FOR "${schemaNameUI}"."UserIdentity";


CREATE TABLE "${schemaNameUI}"."OwnershipUnit" (
  "id"           NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "name"         VARCHAR2(255 BYTE),
  "publicRead"   NUMBER(1, 0) NOT NULL,
  CONSTRAINT PK_OwnershipUnit PRIMARY KEY ("id"),
  CONSTRAINT CHK_OwnershipUnit_1 CHECK ("publicRead" IN (0, 1))
);
GRANT SELECT ON "${schemaNameUI}"."OwnershipUnit" TO "${schemaNameUI}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameUI}"."OwnershipUnit" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameUI}"."OwnershipUnit" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameUI}"."OwnershipUnit" TO "${schemaNameUI}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameUI}"."OwnershipUnit" FOR "${schemaNameUI}"."OwnershipUnit";


CREATE TABLE "${schemaNameUI}"."JoinUserIdentityOwnershipUnit" (
  "userIdentityId" NUMBER(19, 0) NOT NULL,
  "ownershipUnitId" NUMBER(19, 0) NOT NULL,
  CONSTRAINT PK_JoinUserIdentityOwnershipUnit PRIMARY KEY ("userIdentityId", "ownershipUnitId"),
  CONSTRAINT FK_JoinUserIdentityOwnershipUnit_UserIdentity_1 FOREIGN KEY ("userIdentityId")
    REFERENCES "${schemaNameUI}"."UserIdentity" ("id"),
  CONSTRAINT FK_JoinUserIdentityOwnershipUnit_OwnershipUnit_2 FOREIGN KEY ("ownershipUnitId")
    REFERENCES "${schemaNameUI}"."OwnershipUnit" ("id")
);
GRANT SELECT ON "${schemaNameUI}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameUI}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameUI}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameUI}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameUI}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameUI}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameUI}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameUI}"."JoinUserIdentityOwnershipUnit" FOR "${schemaNameUI}"."JoinUserIdentityOwnershipUnit";

