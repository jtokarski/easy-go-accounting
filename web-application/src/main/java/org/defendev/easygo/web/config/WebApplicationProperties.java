package org.defendev.easygo.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "easygo.web-application")
public class WebApplicationProperties {

    public static class Oidc {

        public static class Azure {

            private String clientId;

            private String clientSecret;

            private String authorizationUri;

            private String tokenUri;

            private String userInfoUri;

            private String jwkSetUri;

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

            public String getAuthorizationUri() {
                return authorizationUri;
            }

            public void setAuthorizationUri(String authorizationUri) {
                this.authorizationUri = authorizationUri;
            }

            public String getTokenUri() {
                return tokenUri;
            }

            public void setTokenUri(String tokenUri) {
                this.tokenUri = tokenUri;
            }

            public String getUserInfoUri() {
                return userInfoUri;
            }

            public void setUserInfoUri(String userInfoUri) {
                this.userInfoUri = userInfoUri;
            }

            public String getJwkSetUri() {
                return jwkSetUri;
            }

            public void setJwkSetUri(String jwkSetUri) {
                this.jwkSetUri = jwkSetUri;
            }
        }

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

        private Azure azure;

        private Github github;

        public Azure getAzure() {
            return azure;
        }

        public void setAzure(Azure azure) {
            this.azure = azure;
        }

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
