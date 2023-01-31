
ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN'
;

-- ----------------------------------- --
-- -------------- FA ----------------- --
-- ----------------------------------- --
BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${roUserNameFA}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${appUserNameFA}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP ROLE "${schemaNameFA}_READONLY_ROLE"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1919 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP ROLE "${schemaNameFA}_UPDATE_ROLE"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1919 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${schemaNameFA}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;


-- ----------------------------------- --
-- -------------- SE ----------------- --
-- ----------------------------------- --
BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${roUserNameSE}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${appUserNameSE}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP ROLE "${schemaNameSE}_READONLY_ROLE"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1919 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP ROLE "${schemaNameSE}_UPDATE_ROLE"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1919 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${schemaNameSE}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;


-- ----------------------------------- --
-- -------------- UI ----------------- --
-- ----------------------------------- --
BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${roUserNameUI}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${appUserNameUI}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP ROLE "${schemaNameUI}_READONLY_ROLE"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1919 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP ROLE "${schemaNameUI}_UPDATE_ROLE"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1919 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;

BEGIN
  EXECUTE IMMEDIATE 'DROP USER "${schemaNameUI}" CASCADE';
EXCEPTION
  WHEN OTHERS THEN
    IF (-1918 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;


-- ----------------------------------- --
-- ------------ (SYS) ---------------- --
-- ----------------------------------- --
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE "EASYGO_SYS"."${flywaySchemaHistoryTableName}"';
EXCEPTION
  WHEN OTHERS THEN
    IF (-942 != SQLCODE) THEN
      RAISE;
    END IF;
END;
;


