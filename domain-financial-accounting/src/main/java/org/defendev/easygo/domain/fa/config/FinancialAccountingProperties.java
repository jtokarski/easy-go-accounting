package org.defendev.easygo.domain.fa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;



@ConfigurationProperties(prefix = "easygo.financial-accounting")
public class FinancialAccountingProperties {

    public static class Db {

        public static class Oracle {

            private String tenantId;

            private String driver;

            private String host;

            private int port;

            private String containerName;

            private String appUserPassword;

            private String appUserName;

            public String getTenantId() {
                return tenantId;
            }

            public void setTenantId(String tenantId) {
                this.tenantId = tenantId;
            }

            public String getDriver() {
                return driver;
            }

            public void setDriver(String driver) {
                this.driver = driver;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public String getContainerName() {
                return containerName;
            }

            public void setContainerName(String containerName) {
                this.containerName = containerName;
            }

            public String getAppUserPassword() {
                return appUserPassword;
            }

            public void setAppUserPassword(String appUserPassword) {
                this.appUserPassword = appUserPassword;
            }

            public String getAppUserName() {
                return appUserName;
            }

            public void setAppUserName(String appUserName) {
                this.appUserName = appUserName;
            }
        }

        private List<Oracle> oracle;

        public List<Oracle> getOracle() {
            return oracle;
        }

        public void setOracle(List<Oracle> oracle) {
            this.oracle = oracle;
        }
    }

    private Db db;

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

}
