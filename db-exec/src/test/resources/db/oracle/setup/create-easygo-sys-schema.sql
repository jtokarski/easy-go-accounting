
--
-- In case of any errors I want to have a message
-- in american english.
--
ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN';

--
-- Why do we create a USER when we actually want to create
-- a 'schema' or 'database' ?
-- https://stackoverflow.com/a/18404737
--
CREATE USER EASYG_SYS
    NO AUTHENTICATION
    CONTAINER = CURRENT
    DEFAULT TABLESPACE SYSTEM
    QUOTA 50M ON SYSTEM;
