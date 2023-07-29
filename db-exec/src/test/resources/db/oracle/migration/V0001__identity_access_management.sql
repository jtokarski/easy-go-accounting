
-- Convention: GQ Id 519

ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN';

CREATE USER "${schemaNameIAM}"
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
CREATE ROLE "${schemaNameIAM}_READONLY_ROLE";
CREATE ROLE "${schemaNameIAM}_UPDATE_ROLE";

-- User Identity application user
CREATE USER "${appUserNameIAM}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${appUserPasswordIAM}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameIAM}_READONLY_ROLE" TO "${appUserNameIAM}";
GRANT "${schemaNameIAM}_UPDATE_ROLE" TO "${appUserNameIAM}";
ALTER USER "${appUserNameIAM}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${appUserNameIAM}";

-- User Identity read-only (reporting) user
CREATE USER "${roUserNameIAM}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${roUserPasswordIAM}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameIAM}_READONLY_ROLE" TO "${roUserNameIAM}";
ALTER USER "${roUserNameIAM}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${roUserNameIAM}";


CREATE TABLE "${schemaNameIAM}"."PrivilegeKey" (
  "id"           VARCHAR2(255 BYTE),
  CONSTRAINT PK_PrivilegeKey PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameIAM}"."PrivilegeKey" TO "${schemaNameIAM}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameIAM}"."PrivilegeKey" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameIAM}"."PrivilegeKey" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameIAM}"."PrivilegeKey" TO "${schemaNameIAM}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameIAM}"."PrivilegeKey" FOR "${schemaNameIAM}"."PrivilegeKey";
INSERT ALL
  INTO "${schemaNameIAM}"."PrivilegeKey"("id") VALUES ('preview')
  INTO "${schemaNameIAM}"."PrivilegeKey"("id") VALUES ('read')
  INTO "${schemaNameIAM}"."PrivilegeKey"("id") VALUES ('write')
  INTO "${schemaNameIAM}"."PrivilegeKey"("id") VALUES ('own')
  SELECT 1 FROM DUAL;


CREATE TABLE "${schemaNameIAM}"."OwnershipUnit" (
  "id"           NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "name"         VARCHAR2(255 BYTE),
  CONSTRAINT PK_OwnershipUnit PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameIAM}"."OwnershipUnit" TO "${schemaNameIAM}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameIAM}"."OwnershipUnit" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameIAM}"."OwnershipUnit" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameIAM}"."OwnershipUnit" TO "${schemaNameIAM}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameIAM}"."OwnershipUnit" FOR "${schemaNameIAM}"."OwnershipUnit";


CREATE TABLE "${schemaNameIAM}"."OwnershipUnitAnonymousPrivilege" (
  "ownershipUnitId" NUMBER(19, 0) NOT NULL,
  "privilegeKey" VARCHAR2(255 BYTE) NOT NULL,
  CONSTRAINT PK_OwnershipUnitAnonymousPrivilege PRIMARY KEY ("ownershipUnitId", "privilegeKey"),
  CONSTRAINT FK_OwnershipUnitAnonymousPrivilege_PrivilegeKey_1 FOREIGN KEY ("privilegeKey")
    REFERENCES "${schemaNameIAM}"."PrivilegeKey" ("id")
);
GRANT SELECT ON "${schemaNameIAM}"."OwnershipUnitAnonymousPrivilege" TO "${schemaNameIAM}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameIAM}"."OwnershipUnitAnonymousPrivilege" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameIAM}"."OwnershipUnitAnonymousPrivilege" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameIAM}"."OwnershipUnitAnonymousPrivilege" TO "${schemaNameIAM}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameIAM}"."OwnershipUnitAnonymousPrivilege" FOR "${schemaNameIAM}"."OwnershipUnitAnonymousPrivilege";


CREATE TABLE "${schemaNameIAM}"."UserIdentity" (
  "id"           NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "username"     VARCHAR2(255 BYTE),
  "password"     VARCHAR2(255 BYTE),
  "oidcProvider" VARCHAR2(255 BYTE),
  "oidcSub"      VARCHAR2(255 BYTE),
  CONSTRAINT PK_UserIdentity PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameIAM}"."UserIdentity" TO "${schemaNameIAM}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameIAM}"."UserIdentity" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameIAM}"."UserIdentity" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameIAM}"."UserIdentity" TO "${schemaNameIAM}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameIAM}"."UserIdentity" FOR "${schemaNameIAM}"."UserIdentity";


CREATE TABLE "${schemaNameIAM}"."JoinUserIdentityOwnershipUnit" (
  "userIdentityId" NUMBER(19, 0) NOT NULL,
  "ownershipUnitId" NUMBER(19, 0) NOT NULL,
  "privilege" VARCHAR2(255 BYTE) NOT NULL,
  CONSTRAINT PK_JoinUserIdentityOwnershipUnit PRIMARY KEY ("userIdentityId", "ownershipUnitId"),
  CONSTRAINT FK_JoinUserIdentityOwnershipUnit_UserIdentity_1 FOREIGN KEY ("userIdentityId")
    REFERENCES "${schemaNameIAM}"."UserIdentity" ("id"),
  CONSTRAINT FK_JoinUserIdentityOwnershipUnit_OwnershipUnit_2 FOREIGN KEY ("ownershipUnitId")
    REFERENCES "${schemaNameIAM}"."OwnershipUnit" ("id"),
  CONSTRAINT FK_JoinUserIdentityOwnershipUnit_PrivilegeKey_3 FOREIGN KEY ("privilege")
    REFERENCES "${schemaNameIAM}"."PrivilegeKey" ("id")
);
GRANT SELECT ON "${schemaNameIAM}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameIAM}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameIAM}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameIAM}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameIAM}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameIAM}"."JoinUserIdentityOwnershipUnit" TO "${schemaNameIAM}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameIAM}"."JoinUserIdentityOwnershipUnit" FOR "${schemaNameIAM}"."JoinUserIdentityOwnershipUnit";

