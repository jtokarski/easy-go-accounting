
ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN';

CREATE USER "${schemaNameFA}"
  NO AUTHENTICATION
  CONTAINER = CURRENT
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;

-- Roles for accessing Financial Accounting schema
CREATE ROLE "${schemaNameFA}_READONLY_ROLE";
CREATE ROLE "${schemaNameFA}_UPDATE_ROLE";

-- Stock Exchange application user
CREATE USER "${appUserNameFA}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${appUserPasswordFA}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameFA}_READONLY_ROLE" TO "${appUserNameFA}";
GRANT "${schemaNameFA}_UPDATE_ROLE" TO "${appUserNameFA}";
ALTER USER "${appUserNameFA}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${appUserNameFA}";


-- Stock Exchange read-only (reporting) user
CREATE USER "${roUserNameFA}"
  CONTAINER = CURRENT
  IDENTIFIED BY "${roUserPasswordFA}"
  DEFAULT TABLESPACE SYSTEM
  QUOTA 50M ON SYSTEM;
GRANT "${schemaNameFA}_READONLY_ROLE" TO "${roUserNameFA}";
ALTER USER "${roUserNameFA}" DEFAULT ROLE ALL;
GRANT CREATE SESSION TO "${roUserNameFA}";








CREATE TABLE "${schemaNameFA}"."SourceDocument" (
  "id"                    NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "controlNumber"         VARCHAR2(100 CHAR),
  "documentDateTimeZulu"  DATE,
  "description"           VARCHAR2(500 CHAR),
   CONSTRAINT PK_SourceDocument PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameFA}"."SourceDocument" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."SourceDocument" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."SourceDocument" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."SourceDocument" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."SourceDocument" FOR "${schemaNameFA}"."SourceDocument";


CREATE TABLE "${schemaNameFA}"."FinancialTransaction" (
  "id"                       NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "sourceDocumentId"         NUMBER(19, 0),
  "recordedBy"               VARCHAR2(50 CHAR),
  "memo"                     VARCHAR2(500 CHAR),
  "recordedDateTimeZulu"     DATE,
  "transactionDateTimeZulu"  DATE,
  CONSTRAINT PK_FinancialTransaction PRIMARY KEY ("id"),
  CONSTRAINT FK_FinancialTransaction_SourceDocument_1 FOREIGN KEY ("sourceDocumentId")
    REFERENCES "${schemaNameFA}"."SourceDocument" ("id")
);
GRANT SELECT ON "${schemaNameFA}"."FinancialTransaction" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."FinancialTransaction" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."FinancialTransaction" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."FinancialTransaction" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."FinancialTransaction" FOR "${schemaNameFA}"."FinancialTransaction";


CREATE TABLE "${schemaNameFA}"."GlAccount" (
  "id"              NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "accountName"     VARCHAR2(120 CHAR),
  "accountNumber"   VARCHAR2(50 CHAR),
   CONSTRAINT PK_GlAccount PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameFA}"."GlAccount" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."GlAccount" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."GlAccount" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."GlAccount" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."GlAccount" FOR "${schemaNameFA}"."GlAccount";


CREATE TABLE "${schemaNameFA}"."GlPosting" (
  "id"                      NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "financialTransactionId"  NUMBER(19, 0) NOT NULL,
  "glAccountId"             NUMBER(19, 0) NOT NULL,
  "debitOrCredit"           VARCHAR2(1 CHAR) NOT NULL,
  "amount"                  NUMBER(16, 2) NOT NULL,
  CONSTRAINT PK_GlPosting PRIMARY KEY ("id"),
  CONSTRAINT FK_GlPosting_FinancialTransaction_1 FOREIGN KEY ("financialTransactionId")
    REFERENCES "${schemaNameFA}"."FinancialTransaction" ("id"),
  CONSTRAINT FK_GlPosting_GlAccount_2 FOREIGN KEY ("glAccountId")
    REFERENCES "${schemaNameFA}"."GlAccount" ("id"),
--
-- Simulating ENUMs in Oracle Database. The IN condition here is case-sensitive, so it doesn't allow
-- uppercase 'D' or 'C'.
-- See:
--   - [SQL Language Reference / 8 Common SQL DDL Clauses / constraint]
--     https://docs.oracle.com/en/database/oracle/oracle-database/19/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
--   - [SQL Language Reference / 6 Conditions / IN Condition]
--     https://docs.oracle.com/en/database/oracle/oracle-database/19/sqlrf/IN-Condition.html#GUID-C7961CB3-8F60-47E0-96EB-BDCF5DB1317C
--
  CONSTRAINT CHK_GlPosting_1 CHECK ("debitOrCredit" IN ('d', 'c'))
);
GRANT SELECT ON "${schemaNameFA}"."GlPosting" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."GlPosting" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."GlPosting" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."GlPosting" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."GlPosting" FOR "${schemaNameFA}"."GlPosting";


CREATE TABLE "${schemaNameFA}"."SlAccount" (
  "id"              NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "accountName"     VARCHAR2(120 CHAR),
  "accountNumber"   VARCHAR2(50 CHAR),
  CONSTRAINT PK_SlAccount PRIMARY KEY ("id")
);
GRANT SELECT ON "${schemaNameFA}"."SlAccount" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."SlAccount" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."SlAccount" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."SlAccount" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."SlAccount" FOR "${schemaNameFA}"."SlAccount";


CREATE TABLE "${schemaNameFA}"."SlPosting" (
  "id"           NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "glPostingId"  NUMBER(19, 0) NOT NULL,
  "slAccountId"  NUMBER(19, 0) NOT NULL,
  "amount"       NUMBER(16, 2) NOT NULL,
  CONSTRAINT PK_SlPosting PRIMARY KEY ("id"),
  CONSTRAINT FK_SlPosting_GlPosting_1 FOREIGN KEY ("glPostingId")
    REFERENCES "${schemaNameFA}"."GlPosting" ("id"),
  CONSTRAINT FK_SlPosting_SlAccount_2 FOREIGN KEY ("slAccountId")
    REFERENCES "${schemaNameFA}"."SlAccount" ("id")
);
GRANT SELECT ON "${schemaNameFA}"."SlPosting" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."SlPosting" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."SlPosting" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."SlPosting" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."SlPosting" FOR "${schemaNameFA}"."SlPosting";


CREATE TABLE "${schemaNameFA}"."RetailBankingAccount" (
  "id"           NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY (NOCACHE) NOT NULL,
  "slAccountId"  NUMBER(19, 0) NOT NULL,
  CONSTRAINT PK_RetailBankingAccount PRIMARY KEY ("id"),
  CONSTRAINT FK_RetailBankingAccount_SlAccount_1 FOREIGN KEY ("slAccountId")
    REFERENCES "${schemaNameFA}"."SlAccount" ("id")
);
GRANT SELECT ON "${schemaNameFA}"."RetailBankingAccount" TO "${schemaNameFA}_READONLY_ROLE";
GRANT INSERT ON "${schemaNameFA}"."RetailBankingAccount" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT UPDATE ON "${schemaNameFA}"."RetailBankingAccount" TO "${schemaNameFA}_UPDATE_ROLE";
GRANT DELETE ON "${schemaNameFA}"."RetailBankingAccount" TO "${schemaNameFA}_UPDATE_ROLE";
CREATE SYNONYM "${appUserNameFA}"."RetailBankingAccount" FOR "${schemaNameFA}"."RetailBankingAccount";





-- subsidiary ledger account
-- in Polish "konto analityczne"
-- SlAccount

-- subsidiary ledger
-- in Polish "księga pomocnicza"
-- SlPosting





