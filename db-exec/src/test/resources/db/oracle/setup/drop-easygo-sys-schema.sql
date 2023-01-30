
--
-- In case of any errors I want to have a message
-- in american english.
--
ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN'
;

BEGIN
    EXECUTE IMMEDIATE 'DROP USER "EASYG_SYS" CASCADE';
EXCEPTION
    WHEN OTHERS THEN
        IF -1918 != SQLCODE THEN
            RAISE;
        END IF;
END;
;

