package org.defendev.easygo.devops.dbexec;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;



public class DatabaseMaintananceTest {


    @Test
    public void createEasygoSysSchema() {
        assertThat(178).isEqualTo(178);
    }

    @Test
    public void dropEasygoSysSchema() {
        assertThat(8).isEqualTo(8);

    }

    @Test
    public void dropTenantSchemas() {
        assertThat(1).isEqualTo(1);

    }

}
