package org.defendev.easygo.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "easygo.web-application")
public class WebApplicationProperties {

    public static class Oidc {

        public static class Github {

            private String clientId;

            private String clientSecret;

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }
        }

        private Github github;

        public Github getGithub() {
            return github;
        }

        public void setGithub(Github github) {
            this.github = github;
        }
    }

    private Oidc oidc;

    public Oidc getOidc() {
        return oidc;
    }

    public void setOidc(Oidc oidc) {
        this.oidc = oidc;
    }
}
