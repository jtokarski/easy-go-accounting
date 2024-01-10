package org.defendev.easygo.domain.iam.service;

import org.defendev.common.domain.command.result.CommandResult;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.easygo.domain.iam.api.IEasygoOidcUserService;
import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.defendev.easygo.domain.iam.repository.UserIdentityRepo;
import org.defendev.easygo.domain.iam.service.command.RegisterOAuthUserIdentityCommand;
import org.defendev.easygo.domain.iam.service.dto.EasygoOidcUser;
import org.defendev.easygo.domain.iam.service.dto.EasygoRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.Set;



@Component
public class EasygoOidcUserService extends OidcUserService implements IEasygoOidcUserService {

    private final UserIdentityRepo userIdentityRepo;

    private final IRegisterOAuthUserIdentityService registerOAuthUserIdentityService;

    @Autowired
    public EasygoOidcUserService(
        UserIdentityRepo userIdentityRepo,
        IRegisterOAuthUserIdentityService registerOAuthUserIdentityService
    ) {
        this.userIdentityRepo = userIdentityRepo;
        this.registerOAuthUserIdentityService = registerOAuthUserIdentityService;
    }

    @Transactional(transactionManager = "iamJpaTransactionManager")
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser defaultOidcUser = super.loadUser(userRequest);
        final OidcIdToken idToken = defaultOidcUser.getIdToken();
        final OidcUserInfo userInfo = defaultOidcUser.getUserInfo();
        final String provider = userRequest.getClientRegistration().getRegistrationId();

        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit =
            findOrRegisterOAuthUserIdentity(provider, idToken.getSubject())
            .map(UserIdentity::getPrivilegeToOwnershipUnit)
            .orElseGet(Map::of);

        return new EasygoOidcUser(defaultOidcUser, Set.of(EasygoRoles.fullyAuthenticated()), privilegeToOwnershipUnit);
    }

    private Optional<UserIdentity> findOrRegisterOAuthUserIdentity(String oauthProvider, String oauthUsername) {
        final Optional<UserIdentity> userIdentityOptional = userIdentityRepo.findOAuth(oauthProvider, oauthUsername);
        if (userIdentityOptional.isPresent()) {
            return userIdentityOptional;
        }
        final CommandResult<UserIdentity> result = registerOAuthUserIdentityService.execute(
            new RegisterOAuthUserIdentityCommand(oauthProvider, oauthUsername));
        if (result.isSuccess()) {
            final UserIdentity newOAuthUserIdentity = result.getData();
            return Optional.of(newOAuthUserIdentity);
        } else {
            return Optional.empty();
        }
    }

}
