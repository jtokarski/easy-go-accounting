package org.defendev.easygo.domain.iam.service;

import org.defendev.easygo.domain.iam.api.Privilege;
import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.defendev.easygo.domain.iam.repository.UserIdentityRepo;
import org.defendev.easygo.domain.iam.service.dto.EasygoOidcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class EasygoOAuth2UserService extends OidcUserService {

    private UserIdentityRepo userIdentityRepo;

    @Autowired
    public EasygoOAuth2UserService(UserIdentityRepo userIdentityRepo) {
        this.userIdentityRepo = userIdentityRepo;
    }

    @Transactional(transactionManager = "iamJpaTransactionManager")
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser defaultOidcUser = super.loadUser(userRequest);
        final OidcIdToken idToken = defaultOidcUser.getIdToken();
        final OidcUserInfo userInfo = defaultOidcUser.getUserInfo();

        final String issuerHost = idToken.getIssuer().getHost();
        final String provider;
        if (issuerHost.contains("microsoft")) {
            provider = "azure";
        } else if (issuerHost.contains("google")) {
            provider = "google";
        } else {
            provider = "-";
        }

        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit = userIdentityRepo
            .findOidcSub(provider, idToken.getSubject())
            .map(UserIdentity::getPrivilegeToOwnershipUnit)
            .orElseGet(Map::of);

        return new EasygoOidcUser(List.of(new SimpleGrantedAuthority("ROLE_USER")), idToken, userInfo,
            privilegeToOwnershipUnit);
    }

}
