
--
-- !!!
-- TODO: Rewrite SEQUENCE to GENERATED ALWAYS AS IDENTITY (NOCACHE)
-- !!!
--
--



ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN';

CREATE USER "${schemaNameSE}"
  NO AUTHENTICATION
  CONTAINER = CURRENT
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;

GRANT REFERENCES ("id") ON "${schemaNameIAM}"."UserIdentity" TO "${schemaNameSE}";

-- Roles for accessing Stock Exchange schema
CREATE ROLE "${schemaNameSE}_READONLY_ROLE";
CREATE ROLE "${schemaNameSE}_UPDATE_ROLE";

-- Stock Exchange application user
CREATE USER "${appUserNameSE}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${appUserPasswordSE}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameSE}_READONLY_ROLE" TO "${appUserNameSE}";
GRANT "${schemaNameSE}_UPDATE_ROLE" TO "${appUserNameSE}";
ALTER USER "${appUserNameSE}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${appUserNameSE}";


-- Stock Exchange read-only (reporting) user
CREATE USER "${roUserNameSE}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${roUserPasswordSE}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameSE}_READONLY_ROLE" TO "${roUserNameSE}";
ALTER USER "${roUserNameSE}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${roUserNameSE}";


-- Stock table
CREATE TABLE "${schemaNameSE}"."Stock" (
  "id"           NUMBER(19, 0),
  "stockSymbol"  VARCHAR2(255 BYTE),
  "stockName"    VARCHAR2(255 BYTE),
  "marketName"   VARCHAR2(255 BYTE),
  CONSTRAINT PK_Stock PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameSE}"."Stock" TO "${schemaNameSE}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameSE}"."Stock" TO "${schemaNameSE}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameSE}"."Stock" TO "${schemaNameSE}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameSE}"."Stock" TO "${schemaNameSE}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameSE}"."Stock" FOR "${schemaNameSE}"."Stock";

CREATE SEQUENCE "${schemaNameSE}"."SEQ_Stock"
  MINVALUE 1 MAXVALUE 999999999999999999999999999
  START WITH 1 INCREMENT BY 1
  CACHE 20
  NOORDER
  NOCYCLE;
GRANT SELECT ON "${schemaNameSE}"."SEQ_Stock" TO "${schemaNameSE}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameSE}"."SEQ_Stock" FOR "${schemaNameSE}"."SEQ_Stock";


-- Trader table
CREATE TABLE "${schemaNameSE}"."Trader" (
  "id"              NUMBER(19, 0),
  "firstName"       VARCHAR2(255 BYTE),
  "lastName"        VARCHAR2(255 BYTE),
  "userIdentityId"  NUMBER(19, 0),
  CONSTRAINT PK_Trader PRIMARY KEY ("id"),
  CONSTRAINT FK_Trader_UserIdentity_1
    FOREIGN KEY ("userIdentityId")
    REFERENCES "${schemaNameIAM}"."UserIdentity" ("id")
);
GRANT SELECT ON "${schemaNameSE}"."Trader" TO "${schemaNameSE}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameSE}"."Trader" TO "${schemaNameSE}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameSE}"."Trader" TO "${schemaNameSE}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameSE}"."Trader" TO "${schemaNameSE}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameSE}"."Trader" FOR "${schemaNameSE}"."Trader";

CREATE SEQUENCE "${schemaNameSE}"."SEQ_Trader"
  MINVALUE 1 MAXVALUE 999999999999999999999999999
  START WITH 1 INCREMENT BY 1
  CACHE 20
  NOORDER
  NOCYCLE;
GRANT SELECT ON "${schemaNameSE}"."SEQ_Trader" TO "${schemaNameSE}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameSE}"."SEQ_Trader" FOR "${schemaNameSE}"."SEQ_Trader";


-- Order table
CREATE TABLE "${schemaNameSE}"."Order" (
  "id"              NUMBER(19, 0),
  "price"           NUMBER(19, 0),
  "quantity"        NUMBER(19, 0),
  "orderAction"     VARCHAR2(255 BYTE),
  "registeredAt"    TIMESTAMP (2),
  "traderId"        NUMBER(19, 0),
  CONSTRAINT PK_Order PRIMARY KEY ("id"),
  CONSTRAINT FK_Order_Trader_1
    FOREIGN KEY ("traderId")
    REFERENCES "${schemaNameSE}"."Trader"("id")
);
GRANT SELECT ON "${schemaNameSE}"."Order" TO "${schemaNameSE}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameSE}"."Order" TO "${schemaNameSE}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameSE}"."Order" TO "${schemaNameSE}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameSE}"."Order" TO "${schemaNameSE}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameSE}"."Order" FOR "${schemaNameSE}"."Order";

CREATE SEQUENCE "${schemaNameSE}"."SEQ_Order"
  MINVALUE 1 MAXVALUE 999999999999999999999999999
  START WITH 1 INCREMENT BY 1
  CACHE 20
  NOORDER
  NOCYCLE;
GRANT SELECT ON "${schemaNameSE}"."SEQ_Order" TO "${schemaNameSE}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameSE}"."SEQ_Order" FOR "${schemaNameSE}"."SEQ_Order";

